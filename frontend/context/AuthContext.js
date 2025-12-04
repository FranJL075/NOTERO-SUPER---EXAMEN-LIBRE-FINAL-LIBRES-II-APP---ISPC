import React, { createContext, useState, useContext } from 'react';
import * as SecureStore from 'expo-secure-store';
import api from '../services/api';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(false);

  const login = async (email, password) => {
    // TODO: implement backend login when endpoint available
    setLoading(true);
    try {
      // const res = await api.post('/auth/login', { email, password });
      // await SecureStore.setItemAsync('token', res.data.token);
      // setUser(res.data.user);
      setUser({ email }); // placeholder
    } finally {
      setLoading(false);
    }
  };

  const register = async (data) => {
    setLoading(true);
    try {
      await api.post('/auth/register', data);
      await login(data.email, data.contrasena);
    } finally {
      setLoading(false);
    }
  };

  const logout = async () => {
    await SecureStore.deleteItemAsync('token');
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, loading, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
