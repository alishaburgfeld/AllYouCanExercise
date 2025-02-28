export default {
  presets: [
    [
      "@babel/preset-env",
      {
        targets: "defaults",
        modules: "auto", // Ensures Babel does not overwrite ESM imports
      },
    ],
    [
      "@babel/preset-react",
      {
        runtime: "automatic",
      },
    ],
  ],
  plugins: ["babel-plugin-transform-import-meta"],
};
