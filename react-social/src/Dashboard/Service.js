import { React, useRef } from 'react';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import '../styles/Service.css'

export default function Service( {service, getJSON} ) {

    const userSearchRef = useRef();
    function handleWidgetSubmit(e) {
        getJSON(e, service.value, userSearchRef.current.value);
        userSearchRef.current.value = null;
    }
    
    return service.value === null ? (<div></div>) :
    (
        <div className='servicesElem' key={service.value}>
            <h1>{service.title}</h1>
                <Form onSubmit={(e) => handleWidgetSubmit(e)}>
                    <Form.Label htmlFor={service.labelFor}>{service.label}</Form.Label>
                    <Form.Control ref={userSearchRef} type="text" name={service.inputName}></Form.Control>
                    <Button className='serviceButton' name={service.value} type="submit" value="Rechercher">Research</Button>
                </Form>
        </div>
    )
}

//onChange={handleWidgets}
