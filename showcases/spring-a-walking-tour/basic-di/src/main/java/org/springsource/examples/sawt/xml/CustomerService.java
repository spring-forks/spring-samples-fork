package org.springsource.examples.sawt.xml;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("unused")
public class CustomerService {

	private Log log = LogFactory.getLog(getClass());

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		log.info("setting dataSource" + dataSource.toString());
	}

}
