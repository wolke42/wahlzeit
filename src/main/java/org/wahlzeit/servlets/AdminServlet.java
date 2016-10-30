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

package org.wahlzeit.servlets;

import org.wahlzeit.handlers.PartUtil;
import org.wahlzeit.main.ServiceMain;
import org.wahlzeit.services.LogBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * The servlet for managing administrative system functions.
 */
public class AdminServlet extends AbstractServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 42L; // any one does; class
														// never serialized

	private static final Logger log = Logger.getLogger(AdminServlet.class.getName());

	/**
	 *
	 */
	@Override
	public void myGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String link = request.getRequestURI();
		log.info(LogBuilder.createUserMessage().addParameter("requested URI", link).toString());
		if (isLocalHost(request)) {
			ServiceMain.getInstance().requestStop();
			displayNullPage(request, response);
		} else if (link.length() == "/admin".length()) {
			redirectRequest(response, PartUtil.DEFAULT_PAGE_NAME);
		} else {
			redirectRequest(response, "../" + PartUtil.DEFAULT_PAGE_NAME);
		}
	}

}
