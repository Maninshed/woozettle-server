const fetch = require('node-fetch'); // Required for Node

module.exports = async (req, res) => {
  try {
    const response = await fetch('https://southvilletrading.store/wp-json/custom-api/v1/products');
    const products = await response.json();

    if (!Array.isArray(products)) {
      throw new Error('Invalid products response');
    }

    res.status(200).json({ success: true, products });
  } catch (error) {
    console.error('Fetch failed:', error);
    res.status(500).json({ success: false, error: error.message });
  }
};

