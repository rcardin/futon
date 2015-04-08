/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Riccardo Cardin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.futon.utils;

import org.futon.exceptions.ConfigurationException;

import java.io.IOException;
import java.util.Properties;

/**
 * Configuration properties of {@code futon}. The file read has to be called
 * {@code futon.properties} and it is to be placed in the classpath.
 *
 * @see org.futon.exceptions.ConfigurationException
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
public enum FProperties {
    // Singleton.
    INSTANCE;

    private static final String PROPS_FILE_NAME = "futon.properties";

    // Futon properties file
    private final Properties props;

    /**
     * Ctor.
     *
     * @throws ConfigurationException If the properties file can not be loaded.
     */
    private FProperties() throws ConfigurationException {
        // Load properties
        props = new Properties();
        try {
            props.load(Thread.currentThread()
                             .getContextClassLoader()
                             .getResourceAsStream(PROPS_FILE_NAME));
        } catch (IOException e) {
            throw new ConfigurationException(e);
        }
    }

    /**
     * Returns the value of the property, or its default value.
     *
     * @param property The property
     * @return The value of the property
     */
    public String get(Property property) {
        return props.getProperty(property.getKey(), property.getDefaultValue());
    }

    /**
     * Possible properties used in {@code futon}.
     */
    public enum Property {
        MAX_RETRIES("org.futon.widget.max.retries", "100"),
        RELOAD_RANGE("org.futon.widget.reload.range", "10");
        /**
         * Property key.
         */
        private String key;
        /**
         * Default value.
         */
        private String defaultValue;

        /**
         * Ctor.
         * @param key Property key
         */
        private Property(String key, String defaultValue) {
            this.key = key;
            this.defaultValue = defaultValue;
        }

        /**
         * Returns the property key.
         *
         * @return Property key.
         */
        String getKey() {
            return this.key;
        }

        /**
         * Returns the default value of the property.
         *
         * @return Property's default value
         */
        public String getDefaultValue() {
            return this.defaultValue;
        }
    }
}
