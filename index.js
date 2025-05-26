require('dotenv').config();
const express = require('express');
const axios = require('axios');

const app = express();

const fetchWooProducts = async () => {
  const { WC_STORE_URL, WC_CONSUMER_KEY, WC_CONSUMER_SECRET } = process.env;
  const url = `${WC_STORE_URL}/wp-json/wc/v3/products`;

  const response = await axios.get(url, {
    auth: {
      username: WC_CONSUMER_KEY,
      password: WC_CONSUMER_SECRET
    },
    params: {
      per_page: 10,
      page: 1
    }
  });

  return response.data;
};

app.get('/products', async (req, res) => {
  try {
    const products = await fetchWooProducts();
    res.json(products);
  } catch (error) {
    console.error('❌ WooCommerce API error:', error?.response?.data || error.message);
    res.status(500).json({ error: error?.response?.data || error.message });
  }
});

app.get('/', (req, res) => {
  res.send('✅ WooZettle server is running.');
});

// Vercel: must export the app (not call app.listen)
module.exports = app;

