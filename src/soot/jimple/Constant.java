/* Soot - a J*va Optimization Framework
 * Copyright (C) 1997-1999 Raja Vallee-Rai
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

/*
 * Modified by the Sable Research Group and others 1997-1999.  
 * See the 'credits' file distributed with Soot for the complete list of
 * contributors.  (Soot is distributed at http://www.sable.mcgill.ca/soot)
 */





package soot.jimple;

import soot.baf.*;
import soot.*;
import soot.util.*;
import java.util.*;

public abstract class Constant implements Value, ConvertToBaf
{
    public List getUseBoxes()
    {
        return AbstractUnit.emptyList;
    }

    /** Adds a Baf instruction pushing this constant to the stack onto <code>out</code>. */
    public void convertToBaf(JimpleToBafContext context, List out)
    {
        out.add(Baf.v().newPushInst(this));
    }

    /** Clones the current constant.  Not implemented here. */
    public Object clone() 
    {
        throw new RuntimeException();
    }

    /** Returns true if this object is structurally equivalent to c. 
     * For Constants, equality is structural equality, so we just call equals(). */
    public boolean equivTo(Object c)
    {
        return equals(c);
    }
}
