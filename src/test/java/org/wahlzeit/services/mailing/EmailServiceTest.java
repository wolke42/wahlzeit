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
package org.wahlzeit.services.mailing;

import org.wahlzeit.main.ServiceMain;
import org.wahlzeit.services.EmailAddress;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
public class EmailServiceTest {

	/**
	 *
	 */
	protected EmailService emailService = null;

	/**
	 *
	 */
	protected EmailAddress validAddress;
	
	
	@BeforeClass
	public static void setInProduction(){
		ServiceMain.getInstance().setIsInProduction(false);
	}
	

	/**
	 *
	 */
	@Before
	public void setUp() throws Exception {

		emailService = EmailServiceManager.getDefaultService();

		validAddress = EmailAddress.getFromString("test@test.de");
	}

	/**
	 *
	 */
	@Test
	public void testSendInvalidEmail() {
		try {
			assertFalse(emailService.sendEmailIgnoreException(validAddress, null, "lol", "hi"));
			assertFalse(emailService.sendEmailIgnoreException(null, validAddress, null, "body"));
			assertFalse(emailService.sendEmailIgnoreException(validAddress, null, "hi", "       "));
		} catch (Exception ex) {
			fail("Silent mode does not allow exceptions");
		}
	}

	/**
	 *
	 */
	@Test
	public void testSendValidEmail() {
		try {
			assertTrue(emailService.sendEmailIgnoreException(validAddress, validAddress, "hi", "test"));
		} catch (Exception ex) {
			fail("Silent mode does not allow exceptions");
		}
	}
}
