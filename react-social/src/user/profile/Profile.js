import React, { Component } from 'react';
import './Profile.css';
import Card from 'react-bootstrap/Card';

class Profile extends Component {
    constructor(props) {
        super(props);
        console.log(props);
    }
    render() {
        return (
            <div className="background">
                <br /><br /><br /><br />
                <h1 className="home-title text-center">PROFILE</h1>
                <Card className="profilecard">
                    <div className="container">
                        <div className="profile-info">
                            <div className="profile-avatar">
                                {
                                    this.props.currentUser.imageUrl ? (
                                        <img src={this.props.currentUser.imageUrl} alt={this.props.currentUser.name} />
                                    ) : (
                                        <div className="text-avatar">
                                            <span>{this.props.currentUser.name && this.props.currentUser.name[0]}</span>
                                        </div>
                                    )
                                }
                                <h2>{this.props.currentUser.name}</h2>
                                {
                                    // if the email string contains "null"
                                    this.props.currentUser.email.includes("null") ? (
                                        <p className="profile-email">No email yet !</p>
                                        ) : (
                                        <p className="profile-email">{this.props.currentUser.email}</p>
                                    )
                                }
                            </div>
                        </div>
                    </div>
                </Card>
            </div>
        );
    }
}

export default Profile