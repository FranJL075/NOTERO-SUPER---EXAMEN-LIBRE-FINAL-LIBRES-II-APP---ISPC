import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import MainStack from './navigation/MainStack';
import { AuthProvider } from './context/AuthContext';
import { ListProvider } from './context/ListContext';

export default function App() {
  return (
    <AuthProvider>
      <ListProvider>
        <NavigationContainer>
          <MainStack />
        </NavigationContainer>
      </ListProvider>
    </AuthProvider>
  );
}
