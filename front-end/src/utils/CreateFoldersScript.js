import fs from "fs";
import path from "path";

const muscleGroups = [
  "SHOULDERS",
  "CHEST",
  "FOREARMS",
  "OBLIQUES",
  "QUADS",
  "ADDUCTORS",
  "ABS",
  "BICEPS",
  "CARDIO",
  "TRAPS",
  "TRICEPS",
  "ABDUCTORS",
  "HAMSTRINGS",
  "CALVES",
  "LATS",
  "LOWER_BACK",
  "GLUTES",
]; // Add more groups as needed

const imagesDir =
  "/Users/alisha.burgfeld/Documents/AllYouCanExercise/front-end/src/assets/images/";

// Loop through the muscle groups and create folders
muscleGroups.forEach((group) => {
  const folderPath = path.join(imagesDir, group);

  if (!fs.existsSync(folderPath)) {
    fs.mkdirSync(folderPath);
    console.log(`Created folder: ${folderPath}`);
  } else {
    console.log(`Folder already exists: ${folderPath}`);
  }
});

console.log("✅ All folders created!");
