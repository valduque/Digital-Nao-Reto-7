export default {
  testEnvironment: "jsdom",

  transform: {
    "^.+\\.jsx?$": "babel-jest",
  },

  moduleFileExtensions: ["js", "jsx"],

  setupFilesAfterEnv: ["<rootDir>/tests/setupTests.js"],

  testMatch: ["**/tests/**/*.test.js"],
};
