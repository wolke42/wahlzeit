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

package org.wahlzeit.webparts;

import java.io.IOException;
import java.io.Writer;

import org.wahlzeit.model.PatternInstance;

/**
 * A Writable can write a representation of itself to a Writer.
 */

@PatternInstance(
		patternName = "Composite", 
		participants = {
				"Component" 
		}
)
public interface Writable {

	/**
	 *
	 */
	void writeOn(Writer writer) throws IOException;

}
