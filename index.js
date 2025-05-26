require('dotenv').config();
const express = require('express');
const WooCommerceRestApi = require('@woocommerce/woocommerce-rest-api').default;

const app = express();

// 🔧 Initialize WooCommerce API SDK
const api = new WooCommerceRestApi({
  url: process.env.WC_STORE_URL,
  consumerKey: process.env.WC_CONSUMER_KEY,
  consumerSecret: process.env.WC_CONSUMER_SECRET,
  version: 'wc/v3'
});

// 🔁 Fetch products using the SDK
const fetchWooProducts = async () => {
  const response = await api.get('products', {
    per_page: 10,
    page: 1
  });
  return response.data;
};

// 🔗 Route: GET /products
app.get('/products', async (req, res) => {
  try {
    const products = await fetchWooProducts();
    res.status(200).json(products);
  } catch (error) {
    console.error('❌ WooCommerce API error:', error.response?.data || error.message);
    res.status(500).json({
      error: error.response?.data || error.message
    });
  }
});

// 🔗 Route: GET /
app.get('/', (req, res) => {
  res.send('✅ WooZettle server is running via WooCommerce SDK');
});

// ✅ For Vercel deployment — don't use app.listen, export the app
module.exports = app;

