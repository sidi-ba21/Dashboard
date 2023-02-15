import React from 'react'
import Widget from './Widget'

export default function WidgetsList({widgets, removeWidgets}) {
    return (
        widgets.map(widget => {
            return <Widget removeWidgets={removeWidgets} widget={widget} />
        })    
    )
}
