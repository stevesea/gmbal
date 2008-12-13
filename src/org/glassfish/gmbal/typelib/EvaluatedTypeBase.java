/*
 *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 *  Copyright 2001-2008 Sun Microsystems, Inc. All rights reserved.
 * 
 *  The contents of this file are subject to the terms of either the GNU
 *  General Public License Version 2 only ("GPL") or the Common Development
 *  and Distribution License("CDDL") (collectively, the "License").  You
 *  may not use this file except in compliance with the License. You can obtain
 *  a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 *  or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 *  language governing permissions and limitations under the License.
 * 
 *  When distributing the software, include this License Header Notice in each
 *  file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 *  Sun designates this particular file as subject to the "Classpath" exception
 *  as provided by Sun in the GPL Version 2 section of the License file that
 *  accompanied this code.  If applicable, add the following below the License
 *  Header, with the fields enclosed by brackets [] replaced by your own
 *  identifying information: "Portions Copyrighted [year]
 *  [name of copyright owner]"
 * 
 *  Contributor(s):
 * 
 *  If you wish your version of this file to be governed by only the CDDL or
 *  only the GPL Version 2, indicate your decision by adding "[Contributor]
 *  elects to include this software in this distribution under the [CDDL or GPL
 *  Version 2] license."  If you don't indicate a single choice of license, a
 *  recipient has the option to distribute your version of this file under
 *  either the CDDL, the GPL Version 2 or to extend the choice of license to
 *  its licensees as provided above.  However, if you add GPL Version 2 code
 *  and therefore, elected the GPL Version 2 license, then the option applies
 *  only if the new code is made subject to such option by the copyright
 *  holder.
 */

package org.glassfish.gmbal.typelib;

import java.lang.reflect.Modifier;
import java.util.List;

/**
 *
 * @author ken
 */
public abstract class EvaluatedTypeBase implements EvaluatedType {
    
    public <R> R accept( Visitor<R> visitor ) {
        return visitor.visitEvaluatedType( this ) ;
    }

    private String rep = null ;

    public static void handleModifier( StringBuilder sb, int modifiers ) {
        if (Modifier.isPublic( modifiers )) {
            sb.append( "public " ) ;
        } else if (Modifier.isPrivate( modifiers )) {
            sb.append( "private " ) ;
        } else if (Modifier.isProtected( modifiers )) {
            sb.append( "protected " ) ;
        } else if (Modifier.isAbstract( modifiers )) {
            sb.append( "abstract " ) ;
        } else if (Modifier.isNative( modifiers )) {
            sb.append( "native " ) ;
        } else if (Modifier.isStatic( modifiers )) {
            sb.append( "static " ) ;
        } else if (Modifier.isStrict( modifiers )) {
            sb.append( "strictfp " ) ;
        } else if (Modifier.isSynchronized( modifiers )) {
            sb.append( "synchronized " ) ;
        } else if (Modifier.isTransient( modifiers )) {
            sb.append( "transient " ) ;
        } else if (Modifier.isVolatile( modifiers )) {
            sb.append( "volatile " ) ;
        } else if (Modifier.isFinal( modifiers )) {
            sb.append( "Final " ) ;
        }
    }
    
    public static <T> void handleList( StringBuilder sb, List<T> list ) {
        handleList( sb, null, list, null ) ;
    }

    public static <T> void handleList( StringBuilder sb, String start, 
        List<T> list ) {
        
        handleList( sb, start, list, null ) ;
    }

    public static <T> void handleList( StringBuilder sb, String start, 
        List<T> list, String end ) {
        
        if (list.size() > 0) {
            if (start != null) {
                sb.append( start ) ;
            }

            for (T t : list) {
                sb.append( " " ) ;
                sb.append( t.toString() ) ;
            }

            if (end != null) {
                sb.append( end ) ;
            }
        }
    }

    @Override
    public synchronized String toString() {
        if (rep == null) {
            StringBuilder sb = new StringBuilder() ;
            sb.append( "(" ) ;
            makeRepresentation( sb ) ;
            sb.append( ")" ) ;
            rep = sb.toString() ;
        }

        return rep ;
    }

    abstract void makeRepresentation( StringBuilder sb ) ;

    @Override
    public boolean equals( Object obj ) {
        if (this == obj) {
            return true ;
        }
        
        if (this.getClass().isAssignableFrom( obj.getClass() )) {
            return myEquals( obj ) ;
        } else {
            return false ;
        }
    }
    
    abstract boolean myEquals( Object obj ) ;
}