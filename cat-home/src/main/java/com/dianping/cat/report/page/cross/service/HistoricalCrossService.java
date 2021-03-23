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
package com.dianping.cat.report.page.cross.service;

import com.dianping.cat.consumer.cross.CrossAnalyzer;
import com.dianping.cat.consumer.cross.model.entity.CrossReport;
import com.dianping.cat.helper.TimeHelper;
import com.dianping.cat.report.service.BaseHistoricalModelService;
import com.dianping.cat.report.service.ModelRequest;
import org.unidal.lookup.annotation.Inject;

import java.util.Date;

public class HistoricalCrossService extends BaseHistoricalModelService<CrossReport> {

    @Inject
    private CrossReportService m_reportService;

    public HistoricalCrossService() {
        super(CrossAnalyzer.ID);
    }

    @Override
    protected CrossReport buildModel(ModelRequest request) throws Exception {
        String domain = request.getDomain();
        long date = request.getStartTime();
        CrossReport report = getReportFromDatabase(date, domain);

        return report;
    }

    private CrossReport getReportFromDatabase(long timestamp, String domain) throws Exception {
        return m_reportService.queryReport(domain, new Date(timestamp), new Date(timestamp + TimeHelper.ONE_HOUR));
    }

}
