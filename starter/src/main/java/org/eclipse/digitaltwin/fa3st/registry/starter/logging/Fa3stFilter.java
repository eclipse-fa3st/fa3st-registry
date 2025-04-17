/**
 * Copyright (c) 2025 the Eclipse FA³ST Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.eclipse.digitaltwin.fa3st.registry.starter.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.spi.FilterReply;


/**
 * Allows to set different log levels for FA³ST package and all other packages at run-time.
 */
public class Fa3stFilter extends ch.qos.logback.core.filter.Filter<ILoggingEvent> {
    private static final String PACKAGE_FA3ST = "org.eclipse.digitaltwin.fa3st";
    private static Level levelFa3st = Level.WARN;
    private static Level levelExternal = Level.WARN;

    /**
     * Get level of logging for fa3st packages.
     *
     * @return level of logging
     */
    public static Level getLevelFa3st() {
        return levelFa3st;
    }


    /**
     * Set level of logging for fa3st packages.
     *
     * @param level of logging
     */
    public static void setLevelFa3st(Level level) {
        levelFa3st = level;
    }


    /**
     * Get level of logging for external packages.
     *
     * @return level of logging
     */
    public static Level getLevelExternal() {
        return levelExternal;
    }


    /**
     * Set level of logging for external packages.
     *
     * @param level of logging
     */
    public static void setLevelExternal(Level level) {
        levelExternal = level;
    }


    @Override
    public FilterReply decide(ILoggingEvent e) {
        if (e.getLoggerName().startsWith(PACKAGE_FA3ST) && e.getLevel().isGreaterOrEqual(levelFa3st)) {
            return FilterReply.ACCEPT;
        }
        if (!e.getLoggerName().startsWith(PACKAGE_FA3ST) && e.getLevel().isGreaterOrEqual(levelExternal)) {
            return FilterReply.ACCEPT;
        }
        return FilterReply.DENY;
    }
}
