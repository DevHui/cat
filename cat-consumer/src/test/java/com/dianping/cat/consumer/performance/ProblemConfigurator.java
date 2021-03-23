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
package com.dianping.cat.consumer.performance;

import com.dianping.cat.Constants;
import com.dianping.cat.consumer.MockReportManager;
import com.dianping.cat.consumer.problem.ProblemAnalyzer;
import com.dianping.cat.consumer.problem.ProblemDelegate;
import com.dianping.cat.consumer.problem.model.entity.ProblemReport;
import com.dianping.cat.report.ReportDelegate;
import com.dianping.cat.report.ReportManager;
import org.unidal.lookup.annotation.Inject;
import org.unidal.lookup.configuration.AbstractResourceConfigurator;
import org.unidal.lookup.configuration.Component;

import java.util.ArrayList;
import java.util.List;

public class ProblemConfigurator extends AbstractResourceConfigurator {

    public static void main(String[] args) {
        generatePlexusComponentsXmlFile(new ProblemConfigurator());
    }

    protected Class<?> getTestClass() {
        return ProblemPerformanceTest.class;
    }

    @Override
    public List<Component> defineComponents() {
        List<Component> all = new ArrayList<Component>();
        final String ID = ProblemAnalyzer.ID;

        all.add(C(ReportManager.class, ID, MockProblemReportManager.class)//
                .req(ReportDelegate.class, ID));
        all.add(C(ReportDelegate.class, ID, ExtendedProblemDelegate.class));

        return all;
    }

    public static class ExtendedProblemDelegate extends ProblemDelegate {
    }

    public static class MockProblemReportManager extends MockReportManager<ProblemReport> {
        private ProblemReport m_report;

        @Inject
        private ReportDelegate<ProblemReport> m_delegate;

        @Override
        public ProblemReport getHourlyReport(long startTime, String domain, boolean createIfNotExist) {
            if (m_report == null) {
                m_report = (ProblemReport) m_delegate.makeReport(domain, startTime, Constants.HOUR);
            }

            return m_report;
        }

        public void setReport(ProblemReport report) {
            m_report = report;
        }

        @Override
        public void destory() {
        }
    }
}
