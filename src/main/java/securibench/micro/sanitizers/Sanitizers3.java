/*
   Copyright 2006 Benjamin Livshits

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

/**
    @author Benjamin Livshits <livshits@cs.stanford.edu>
    
    $Id: Sanitizers3.java,v 1.4 2006/04/21 17:14:27 livshits Exp $
 */
package securibench.micro.sanitizers;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Locale;
import mockx.servlet.http.HttpServletRequest;
import mockx.servlet.http.HttpServletResponse;
import securibench.micro.BasicTestCase;
import securibench.micro.MicroTestCase;

/** 
 *  @servlet description="safe redirect" 
 *  @servlet vuln_count = "0" 
 *  */
public class Sanitizers3 extends BasicTestCase implements MicroTestCase {
    private static final String FIELD_NAME = "name";

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String s = req.getParameter(FIELD_NAME);
        String name = s.toLowerCase(Locale.UK);

       resp.sendRedirect(URLEncoder.encode("/user/" + name, "UTF-8"));		/* OK */
    }
    
    public String getDescription() {
        return "safe redirect";
    }
    
    public int getVulnerabilityCount() {
        return 0;
    }
}