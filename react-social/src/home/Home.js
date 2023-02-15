import React, { Component } from 'react';
import './Home.css';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import Placeholder from 'react-bootstrap/Placeholder';

class Home extends Component {
    render() {
        return (
            <Card className="text-center">

                <Card.Header><h1 className="home-title">Dashboard Project</h1></Card.Header>
                <Card.Body>
                    
                        <div className="container">
                            <div className="graf-bg-container">
                                <div className="graf-layout">
                                    <div className="graf-circle"></div>
                                    <div className="graf-circle"></div>
                                    <div className="graf-circle"></div>
                                    <div className="graf-circle"></div>
                                    <div className="graf-circle"></div>
                                    <div className="graf-circle"></div>
                                    <div className="graf-circle"></div>
                                    <div className="graf-circle"></div>
                                    <div className="graf-circle"></div>
                                    <div className="graf-circle"></div>
                                    <div className="graf-circle"></div>
                                </div>
                            </div>

                        </div>
                        <br /><br />
                    <Card.Title className="typo">Project informations:</Card.Title>
                    <br />
                    <Card.Text className="typo">
                        Signup on the top right of the screen to create an account and login to the dashboard. <br />
                        If you already have an account, you can login with your credentials. <br />
                        If you are already logged in, you can see your profile and dashboard. <br />
                        Once you are logged in, you can logout from the top right of the screen. <br />
                        You can also see the availabe widgets on the dashboard by clicking on the button below. <br />
                    </Card.Text>
                    <br />
                    <Button href="/Dashboard">View WidgetsList</Button>
                </Card.Body>
            </Card>
        )
    }
}

export default Home;