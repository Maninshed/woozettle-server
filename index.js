require('dotenv').config();
const express = require('express');
const axios = require('axios');

const app = express();
const port = process.env.PORT || 3000;

app.get('/products', async (req, res) => {
  const { WC_STORE_URL, WC_CONSUMER_KEY, WC_CONSUMER_SECRET } = process.env;
  const url = `${WC_STORE_URL}/wp-json/wc/v3/products`;

  try {
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

    res.json(response.data);

  } catch (error) {
    res.status(500).json({
      error: error?.response?.data || error.message
    });
  }
});

app.get('/', (req, res) => {
  res.send('✅ WooZettle server is running.');
});

app.listen(port, () => {
  console.log(`🚀 Server running on port ${port}`);
});


fetchWooProducts();
