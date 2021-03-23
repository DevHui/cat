/*
 * Copyright (c) 2011-2018, Meituan Dianping. All Rights Reserved.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dianping.cat.report.task.reload.impl;

import com.dianping.cat.configuration.NetworkInterfaceManager;
import com.dianping.cat.consumer.business.BusinessAnalyzer;
import com.dianping.cat.consumer.business.BusinessReportMerger;
import com.dianping.cat.consumer.business.model.entity.BusinessReport;
import com.dianping.cat.consumer.business.model.transform.DefaultNativeBuilder;
import com.dianping.cat.core.dal.HourlyReport;
import com.dianping.cat.report.ReportManager;
import com.dianping.cat.report.task.reload.AbstractReportReloader;
import com.dianping.cat.report.task.reload.ReportReloadEntity;
import com.dianping.cat.report.task.reload.ReportReloader;
import org.unidal.lookup.annotation.Inject;
import org.unidal.lookup.annotation.Named;

import java.util.*;
import java.util.Map.Entry;

@Named(type = ReportReloader.class, value = BusinessAnalyzer.ID)
public class BusinessReportReloader extends AbstractReportReloader {

    @Inject(BusinessAnalyzer.ID)
    protected ReportManager<BusinessReport> m_reportManager;

    private List<BusinessReport> buildMergedReports(Map<String, List<BusinessReport>> mergedReports) {
        List<BusinessReport> results = new ArrayList<BusinessReport>();

        for (Entry<String, List<BusinessReport>> entry : mergedReports.entrySet()) {
            String domain = entry.getKey();
            BusinessReport report = new BusinessReport(domain);
            BusinessReportMerger merger = new BusinessReportMerger(report);

            report.setStartTime(report.getStartTime());
            report.setEndTime(report.getEndTime());

            for (BusinessReport r : entry.getValue()) {
                r.accept(merger);
            }
            results.add(merger.getBusinessReport());
        }

        return results;
    }

    @Override
    public String getId() {
        return BusinessAnalyzer.ID;
    }

    @Override
    public List<ReportReloadEntity> loadReport(long time) {
        List<ReportReloadEntity> results = new ArrayList<ReportReloadEntity>();
        Map<String, List<BusinessReport>> mergedReports = new HashMap<String, List<BusinessReport>>();

        for (int i = 0; i < getAnalyzerCount(); i++) {
            Map<String, BusinessReport> reports = m_reportManager.loadLocalReports(time, i);

            for (Entry<String, BusinessReport> entry : reports.entrySet()) {
                String domain = entry.getKey();
                BusinessReport r = entry.getValue();
                List<BusinessReport> rs = mergedReports.get(domain);

                if (rs == null) {
                    rs = new ArrayList<BusinessReport>();

                    mergedReports.put(domain, rs);
                }
                rs.add(r);
            }
        }

        List<BusinessReport> reports = buildMergedReports(mergedReports);

        for (BusinessReport r : reports) {
            HourlyReport report = new HourlyReport();

            report.setCreationDate(new Date());
            report.setDomain(r.getDomain());
            report.setIp(NetworkInterfaceManager.INSTANCE.getLocalHostAddress());
            report.setName(getId());
            report.setPeriod(new Date(time));
            report.setType(1);

            byte[] content = DefaultNativeBuilder.build(r);
            ReportReloadEntity entity = new ReportReloadEntity(report, content);

            results.add(entity);
        }
        return results;
    }
}
