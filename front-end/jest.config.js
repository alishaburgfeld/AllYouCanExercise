export default {
  setupFiles: ["<rootDir>/jest.setup.js"],
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
    ".+\\.(css|styl|less|sass|scss|png|jpg|ttf|woff|woff2)$":
      "jest-transform-stub",
  },
  transformIgnorePatterns: ["<rootDir>/node_modules/(?!axios)"],
  collectCoverageFrom: ["src/**/*.{js,jsx}"],
  setupFilesAfterEnv: ["@testing-library/jest-dom"],
};
