export default {
  setupFiles: ["<rootDir>/jest.setup.js"],
  preset: "react-native", // or any preset you're using, e.g. jest-react
  transform: {
    "^.+\\.(js|jsx|ts|tsx)$": "babel-jest", // Use Babel to transpile the code
  },
  extensionsToTreatAsEsm: [".jsx"],
  globals: {
    jest: {
      useESM: true,
    },
  },
  testEnvironment: "jsdom",
  moduleNameMapper: {
    "^@/(.*)$": "<rootDir>/src/$1",
    "\\.css$": "identity-obj-proxy",
    "\\.(png|jpe?g|gif|webp|svg)$": "<rootDir>/__mocks__/fileMock.js",
  },
  transformIgnorePatterns: ["<rootDir>/node_modules/"],
};
