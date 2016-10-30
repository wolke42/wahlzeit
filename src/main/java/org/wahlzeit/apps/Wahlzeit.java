/*
 * Copyright (c) 2006-2009 by Dirk Riehle, http://dirkriehle.com
 *
 * This file is part of the Wahlzeit photo rating application.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package org.wahlzeit.apps;

import org.wahlzeit.main.ServiceMain;
import org.wahlzeit.services.LogBuilder;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.util.logging.Logger;

/**
 * A simple ServletContextListener to startup and shutdown the Flowers
 * application.
 */
public class Wahlzeit implements ServletContextListener {

	private static final Logger log = Logger.getLogger(Wahlzeit.class.getName());

	/**
	 *
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			ServletContext sc = sce.getServletContext();

			// configures logging
			String contextPath = sc.getContextPath();
			System.setProperty("contextPath", contextPath);
			log.config(LogBuilder.createSystemMessage().addParameter("System property context path", contextPath)
					.toString());

			// determines file system root path to resources
			File dummyFile = new File(sc.getRealPath("dummy.txt"));
			String rootDir = dummyFile.getParent();
			log.config(LogBuilder.createSystemMessage().addParameter("Root directory", rootDir).toString());

			ServiceMain.getInstance().startUp(true, rootDir);
		} catch (Exception ex) {
			log.warning(LogBuilder.createSystemMessage().addException("Initializing context failed", ex).toString());
			throw new RuntimeException("End of story!", ex);
		}
	}

	/**
	 *
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			ServiceMain.getInstance().shutDown();
		} catch (Exception ex) {
			log.warning(LogBuilder.createSystemMessage().addException("Shutting instance down failed", ex).toString());
		}
	}

}
