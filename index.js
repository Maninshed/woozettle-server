// index.js — Vercel-compatible Woo-Zettle sync server using internal WooCommerce API proxy

require('dotenv').config();
const axios = require('axios');
const express = require('express');

const app = express();
const PORT = process.env.PORT || 3000;

app.get('/products', async (req, res) => {
  try {
    const response = await axios.get('https://southvilletrading.store/wp-json/custom-api/v1/products');

    if (!Array.isArray(response.data)) {
      return res.status(500).json({ error: 'Unexpected response from Woo proxy' });
    }

    res.json(response.data);
  } catch (err) {
    console.error('Fetch error:', err.message);
    res.status(500).json({ error: 'Failed to fetch products', message: err.message });
  }
});

// Default route
app.get('/', (req, res) => {
  res.send('WooZettle server is up and running via internal Woo proxy 🚀');
});

app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});
