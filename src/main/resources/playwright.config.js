// playwright.config.js
const { defineConfig } = require('@playwright/test');

module.exports = defineConfig({

  projects: [
    {
      name: 'chromium',
      use: { browserName: 'chromium' },
    },
    {
      name: 'firefox',
      use: { browserName: 'firefox' },
    },
    {
      name: 'webkit',
      use: { browserName: 'webkit' },
    },
  ],

  testDir: './test',

  reporter: [['list'], ['html', { outputFolder: 'test-results', open: 'never' }]],


  timeout: 30000,

  use: {
    headless: false,
    screenshot: 'only-on-failure',
    video: 'retain-on-failure',
  },
});