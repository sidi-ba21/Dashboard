import React from 'react'
import Service from './Service'

export default function ServicesList({services, getJSON}) {

    return (
        services.map(service => {
            return <Service  getJSON={getJSON} key={service.title} service={service} />
        })
    )
}
