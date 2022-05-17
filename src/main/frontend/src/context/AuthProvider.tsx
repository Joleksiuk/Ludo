import React, {createContext, useState} from 'react'
import {AuthState} from '../data-interfaces'

interface AuthStateContext{
    authState: AuthState | undefined,
    setAuth: Function,
}

const AuthContext = createContext({} as AuthStateContext);

export const AuthProvider = ({children}:any) => {

    const [auth, setAuth] =useState<AuthState>();

    return(
        <AuthContext.Provider value={{authState:auth, setAuth:setAuth}}>
            {children}
        </AuthContext.Provider>
    )
}

export default AuthContext;