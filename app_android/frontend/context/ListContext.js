import React, { createContext, useState, useContext } from 'react';

const ListContext = createContext();

export const ListProvider = ({ children }) => {
  const [items, setItems] = useState([]);
  const [budgetLimit, setBudgetLimit] = useState(null);

  const addItem = (product) => {
    setItems((prev) => [...prev, product]);
  };

  const deleteItem = (code) => {
    setItems((prev) => prev.filter((p) => p.codigo !== code));
  };

  const toggleFavorite = (code) => {
    setItems((prev) => prev.map((p) => (p.codigo === code ? { ...p, favorito: !p.favorito } : p)));
  };

  const total = items.reduce((acc, item) => acc + item.precio, 0);

  return (
    <ListContext.Provider
      value={{ items, addItem, deleteItem, toggleFavorite, budgetLimit, setBudgetLimit, total }}
    >
      {children}
    </ListContext.Provider>
  );
};

export const useList = () => useContext(ListContext);
