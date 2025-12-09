import React, { createContext, useState, useContext, useEffect } from 'react';
import * as SecureStore from 'expo-secure-store';
import api, { setAuthToken } from '../services/api';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(false);

  // Load token on mount
  useEffect(() => {
    (async () => {
      const storedToken = await SecureStore.getItemAsync('token');
      if (storedToken) {
        setAuthToken(storedToken);
        try {
          const res = await api.get('/usuarios/yo/'); // TODO: create this endpoint or adjust
          setUser(res.data);
        } catch {
          await SecureStore.deleteItemAsync('token');
        }
      }
    })();
  }, []);

  const login = async (email, password) => {
    setLoading(true);
    try {
      const res = await api.post('/token/', { email, password });
      const { access, refresh } = res.data;
      await SecureStore.setItemAsync('token', access);
      await SecureStore.setItemAsync('refresh', refresh);
      setAuthToken(access);
      // Optionally fetch user profile
      setUser({ email });
    } finally {
      setLoading(false);
    }
  };

  const register = async (data) => {
    setLoading(true);
    try {
      await api.post('/usuarios/registro/', data);
      await login(data.email, data.password);
    } finally {
      setLoading(false);
    }
  };

  const logout = async () => {
    await SecureStore.deleteItemAsync('token');
    await SecureStore.deleteItemAsync('refresh');
    setAuthToken(null);
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, loading, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
