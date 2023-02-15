import React, { Component } from 'react';
import { Link, NavLink } from 'react-router-dom';
import './AppHeader.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';

class AppHeader extends Component {
    render() {
        return (
            <header>
                <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark" className="size">
                    <Container>
                        <Navbar.Brand href="/">Dashboard</Navbar.Brand>
                        <Navbar.Toggle aria-controls="responsive-navbar-nav" />

                        {this.props.authenticated ? (

                            <Nav>
                                <Nav.Link href="/profile">Profile</Nav.Link>

                                <Nav.Link href="/Dashboard">Dashboard</Nav.Link>

                                <Nav.Link onClick={this.props.onLogout}>Logout </Nav.Link>
                            </Nav>

                        ) : (
                            <Nav>
                                <Nav.Link href="/login">Login</Nav.Link>
                                <Nav.Link href="/signup">Signup</Nav.Link>
                            </Nav>
                        )}
                    </Container>
                </Navbar>
            </header>
        )
    }
}

export default AppHeader;