// index.js
const express = require('express');
const WooCommerceRestApi = require('@woocommerce/woocommerce-rest-api').default;
require('dotenv').config();

const app = express();
const port = process.env.PORT || 3000;

// ✅ This is the critical fix:
const WooCommerce = new WooCommerceRestApi({
  url: process.env.WC_STORE_URL,
  consumerKey: process.env.WC_CONSUMER_KEY,
  consumerSecret: process.env.WC_CONSUMER_SECRET,
  version: 'wc/v3',
  queryStringAuth: true // 👈 This pushes auth into the query params instead of headers
});

// 🔁 Product fetch route
app.get('/products', async (req, res) => {
  try {
    const response = await WooCommerce.get('products');
    res.json(response.data);
  } catch (error) {
    console.error('WooCommerce API error:', error.response.data);
    res.status(error.response.status || 500).json(error.response.data);
  }
});

// Optional debug route
app.get('/debug-env', (req, res) => {
  res.json({
    WC_STORE_URL: process.env.WC_STORE_URL,
    WC_CONSUMER_KEY: process.env.WC_CONSUMER_KEY,
    WC_CONSUMER_SECRET: process.env.WC_CONSUMER_SECRET ? '✔️ Present' : '❌ Missing'
  });
});

app.listen(port, () => {
  console.log(`WooZettle server running at http://localhost:${port}`);
});

