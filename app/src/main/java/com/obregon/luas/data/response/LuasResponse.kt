package com.obregon.luas.data.response

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

/**
 * Sample message structure*
 * <stopInfo created="2020-07-09T13:44:15" stop="Busáras" stopAbv="BUS">
        <message>Red Line services operating normally</message>
        <direction name="Inbound">
            <tram dueMins="6" destination="Connolly" />
            <tram dueMins="9" destination="The Point" />
            <tram dueMins="12" destination="The Point" />
            <tram dueMins="17" destination="Connolly
        </direction>
        <direction name="Outbound">
            <tram dueMins="DUE" destination="Tallaght" />
            <tram dueMins="5" destination="Saggart" />
            <tram dueMins="10" destination="Tallaght" />
        </direction>
    </stopInfo>
 */

@Root(name="tram",strict = false)
data class Tram @JvmOverloads constructor( @field:Attribute(name="destination",required = false) var destination:String?=null,
                                           @field:Attribute(name="dueMins",required = false) var dueMins:String?=null)
@Root(name="direction",strict = false)
data class Direction @JvmOverloads constructor(@field:Attribute(name="name",required = false) var name:String?=null,
                                 @field:ElementList(name="tram",inline=true,required = false) var tram:List<Tram>?=null)

@Root(name="stopInfo",strict = false)
data class StopInfo @JvmOverloads constructor (@field:Attribute(name="created",required = false) var created:String?=null,
                                          @field:Attribute(name="stop", required = false) var stop:String?=null,
                                          @field:Attribute(name="stopAbv",required=false) var stopAbv:String?=null,
                                          @field:Element(name="message",required = false) var message:String?=null,
                                          @field:ElementList(name="direction",inline=true) var direction:List<Direction>?=null)