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
package com.dianping.cat.report.task.problem;

import com.dianping.cat.Constants;
import com.dianping.cat.report.page.problem.task.ProblemReportBuilder;
import com.dianping.cat.report.task.TaskBuilder;
import org.junit.Test;
import org.unidal.lookup.ComponentTestCase;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ProblemReportBuilderTest extends ComponentTestCase {

    @Test
    public void testDailyTask() {
        TaskBuilder builder = lookup(TaskBuilder.class, ProblemReportBuilder.ID);

        try {
            builder.buildDailyTask(ProblemReportBuilder.ID, Constants.CAT,
                    new SimpleDateFormat("yyyy-MM-dd").parse("2016-02-25"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWeeklyTask() {
        TaskBuilder builder = lookup(TaskBuilder.class, ProblemReportBuilder.ID);

        try {
            builder.buildWeeklyTask(ProblemReportBuilder.ID, Constants.CAT,
                    new SimpleDateFormat("yyyy-MM-dd").parse("2016-02-20"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMonthlyTask() {
        TaskBuilder builder = lookup(TaskBuilder.class, ProblemReportBuilder.ID);

        try {
            builder.buildMonthlyTask(ProblemReportBuilder.ID, Constants.CAT,
                    new SimpleDateFormat("yyyy-MM-dd").parse("2016-01-01"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
