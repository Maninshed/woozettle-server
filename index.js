require('dotenv').config();
const axios = require('axios');

const { WC_STORE_URL, WC_CONSUMER_KEY, WC_CONSUMER_SECRET } = process.env;

const fetchWooProducts = async () => {
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

    console.log(`✅ Fetched ${response.data.length} products`);
    console.dir(response.data, { depth: null });

  } catch (error) {
    console.error('❌ WooCommerce API error:', error?.response?.data || error.message);
  }
};

fetchWooProducts();
