import React from 'react';
import { View, Text, Button } from 'react-native';
import { useAuth } from '../context/AuthContext';

export default function ProfileScreen({ navigation }) {
  const { user, logout } = useAuth();
  return (
    <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
      <Text>Email: {user?.email}</Text>
      {/* TODO: mostrar listas guardadas y selector de ubicación */}
      <Button title="Cerrar sesión" onPress={logout} />
    </View>
  );
}
