import React, { useState } from 'react';
import { View, TextInput, Button, FlatList, Text, StyleSheet } from 'react-native';
import { useList } from '../context/ListContext';
import api from '../services/api';

export default function ListScreen() {
  const { items, addItem, deleteItem, toggleFavorite, budgetLimit, setBudgetLimit, total } = useList();
  const [codigo, setCodigo] = useState('');

  const handleAdd = async () => {
    try {
      const res = await api.get(`/productos/${codigo}`);
      addItem(res.data);
      setCodigo('');
    } catch (e) {
      alert('Producto no encontrado');
    }
  };

  return (
    <View style={{ flex: 1, padding: 16 }}>
      <Text>Limite de presupuesto: {budgetLimit ?? 'No establecido'}</Text>
      <Button title="Establecer limite" onPress={() => {
        const lim = prompt('Ingrese limite');
        if (lim) setBudgetLimit(parseFloat(lim));
      }} />

      <TextInput placeholder="Código" value={codigo} onChangeText={setCodigo} style={styles.input} />
      <Button title="Agregar" onPress={handleAdd} />
      {/* TODO: barcode scanner button */}

      <FlatList
        data={items}
        keyExtractor={(item) => item.codigo}
        renderItem={({ item }) => (
          <View style={styles.itemRow}>
            <Text>{item.nombre} - ${item.precio.toFixed(2)}</Text>
            <Button title="Fav" onPress={() => toggleFavorite(item.codigo)} />
            <Button title="Del" onPress={() => deleteItem(item.codigo)} />
          </View>
        )}
      />
      <Text>Total: ${total.toFixed(2)}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  input: {
    borderWidth: 1,
    borderColor: '#ccc',
    padding: 8,
    marginVertical: 8,
  },
  itemRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginVertical: 4,
  },
});
