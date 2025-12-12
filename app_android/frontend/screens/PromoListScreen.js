import React from 'react';
import { View, Text } from 'react-native';
import { useList } from '../context/ListContext';

export default function PromoListScreen() {
  const { items, total } = useList();
  const descuento = 0.1; // 10% de descuento
  const totalConDescuento = total * (1 - descuento);

  return (
    <View style={{ flex: 1, padding: 16 }}>
      <Text>Negocio Promocionado - 10% OFF</Text>
      <Text>Total original: ${total.toFixed(2)}</Text>
      <Text>Total con descuento: ${totalConDescuento.toFixed(2)}</Text>
      {/* Podríamos reutilizar la lista del ListScreen aquí */}
    </View>
  );
}
