import React, { useState, useEffect, Component} from 'react'
import {Player} from '../data-interfaces'
import axios from '../axios'
import AuthService from "../services/auth.service";
type Props = {};
type State = {
  username: string,
  email: string,
  password: string,
  successful: boolean,
  message: string
};
export default class Register extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.handleRegister = this.handleRegister.bind(this);
    this.state = {
      username: "",
      password: "",
      successful: false,
      message: ""
    };
  }
  
  handleRegister(formValue: { username: string;  password: string }) {
    const { username, password } = formValue;
    this.setState({
      message: "",
      successful: false
    });
    AuthService.register(
      username,
      password
    ).then(
      response => {
        this.setState({
          message: response.data.message,
          successful: true
        });
      },
      error => {
        const resMessage =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();
        this.setState({
          successful: false,
          message: resMessage
        });
      }
    );
  }
  render() {
    const { successful, message } = this.state;
    const initialValues = {
      username: "",
      email: "",
      password: "",
    };
    return (
      <div className="col-md-12">
        <div className="card card-container">
          
            <form>
              {!successful && (
                <div>
                  <div className="form-group">
                    <label htmlFor="username"> Username </label>
                    <input name="username" type="text" className="form-control" />   
                  </div>
                  <div className="form-group">
                    <label htmlFor="password"> Password </label>
                    <input
                      name="password"
                      type="password"
                      className="form-control"
                    />
                  </div>
                  <div className="form-group">
                    <button type="submit" className="btn btn-primary btn-block">Sign Up</button>
                  </div>
                </div>
              )}
              {message && (
                <div className="form-group">
                  <div
                    className={
                      successful ? "alert alert-success" : "alert alert-danger"
                    }
                    role="alert"
                  >
                    {message}
                  </div>
                </div>
              )}
            </form>
        </div>
      </div>
    );
  }
}