/**
 * Copyright (c) 2015 https://github.com/koori69 All rights reserved.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 *
 */

package org.izumo.ikong.export.bean;

public class ContentBean {

    private Integer Id;

    private Integer T = 0;

    private String C;

    public Integer getId() {
        return this.Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    public Integer getT() {
        return this.T;
    }

    public void setT(Integer t) {
        this.T = t;
    }

    public String getC() {
        return this.C;
    }

    public void setC(String c) {
        this.C = c;
    }
}
