/*
Copyright 2019-present, Simon Data, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at:
http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.simondata.sqlextractor.clients;

import com.amazon.redshift.jdbc42.DataSource;
import com.amazon.redshift.util.RedshiftException;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h1>Redshift Client</h1>
 * Provide facilities for querying to Redshift
 *
 * @author  Chet Mancini
 * @since   2019-03-31
 */
public class RedshiftClient extends AbstractSQLClient {

    private static final int DEFAULT_PORT = 5439;
    private final Logger logger = LoggerFactory.getLogger(RedshiftClient.class);

    /**
     * Constructor
     * @param params SQLParams for the connection.
     */
    public RedshiftClient(SQLParams params) {
        super(params);
    }

    private String getRedshiftURL() {
        return String.format("jdbc:redshift://%s:%s/%s",
                this.params.getHost(), this.params.getPort(DEFAULT_PORT), this.params.getDatabase()
        );
    }

    @Override
    public javax.sql.DataSource initDataSource() {
        DataSource ds = new DataSource();
        ds.setUserID(params.getUser());
        ds.setPassword(params.getPassword());
        ds.setLocale(new Locale(params.getPropertyAsString("language")));
        //ds.setLanguage(params.getPropertyAsString("language"));
        try {
            ds.setURL(this.getRedshiftURL());
        } catch (RedshiftException re) {
            logger.error(re.getMessage(), re);
            return null;
        }
        return ds;
    }

    @Override
    protected String getDriverName() {
        return "com.amazon.redshift.jdbc42.Driver";
    }
}
