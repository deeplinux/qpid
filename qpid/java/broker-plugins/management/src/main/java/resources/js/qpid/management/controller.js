/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */
define(["dojo/dom",
        "dijit/registry",
        "dijit/layout/ContentPane",
        "qpid/management/Broker",
        "qpid/management/VirtualHost",
        "dojo/ready",
        "dojo/domReady!"],
       function (dom, registry, ContentPane, Broker, VirtualHost, ready) {
           var controller = {};

           var constructors = { broker: Broker, virtualhost: VirtualHost };

           var tabDiv = dom.byId("managedViews");

           ready(function() {
               controller.tabContainer = registry.byId("managedViews");
           });


           controller.viewedObjects = {};

           controller.show = function(objType, name) {
               var objId = objType+":"+name;
               if( this.viewedObjects[ objId ] ) {
                   this.tabContainer.selectChild(this.viewedObjects[ objId ].contentPane);
               } else {
                   var Constructor = constructors[ objType ];

                   var obj = new Constructor(name, this);
                   this.viewedObjects[ objId ] = obj;

                   var contentPane = new ContentPane({ region: "center" , title: obj.getTitle()});
                   this.tabContainer.addChild( contentPane );

                   obj.open(contentPane);
                   contentPane.startup();
                   this.tabContainer.selectChild( contentPane );


               }

           };

           ready(function() {
               controller.show("broker","");
               //controller.show("virtualhost","test");
           });


           return controller;
       });