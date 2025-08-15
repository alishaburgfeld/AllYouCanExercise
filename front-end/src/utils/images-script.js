import axios from "axios";
import Cookies from "js-cookie";
import fs from "fs";
import path from "path";

// "Abduction Machine": new URL(
//     "./ABDUCTORS/Abduction Machine.png",
//     import.meta.url,
//   ).href,
const API_BASE_URL = process.env.API_BASE_URL;
const IMAGES = {};

const getExercises = async () => {
  const csrfToken = Cookies.get("X-XSRF-TOKEN");
  try {
    const response = await axios.get(`${API_BASE_URL}/exercises`, {
      headers: {
        "X-XSRF-TOKEN": csrfToken,
      },
      withCredentials: true,
    });
    return response.data;
  } catch (error) {
    console.error("Error fetching exercises:", error);
    return [];
  }
};

const imageExists = (imagePath) => {
  const fullPath = path.resolve(imagePath); // Ensure it resolves to an absolute path
  return fs.existsSync(fullPath); // Checks if the file exists synchronously
};

const addToImages = async () => {
  const exercises = await getExercises(); // Wait for the data from getExercises

  exercises.forEach((exercise) => {
    const imagePath = `./${exercise.exerciseGroup}/${exercise.name}.png`; // Define image path

    // Check if image exists before adding to the IMAGES object
    if (imageExists(imagePath)) {
      if (!IMAGES[exercise.name]) {
        IMAGES[
          exercise.name
        ] = `new URL('./${exercise.exerciseGroup}/${exercise.name}.png', import.meta.url).href`;
        // console.log(IMAGES[exercise.name]);
      } else {
        console.log(exercise.name, "already in images");
      }
    } else {
      console.log(
        `Image for ${exercise.name} does not exist in the folder`,
      );
    }
  });

  updateImagesFile();
};

const updateImagesFile = () => {
  const filePath = "./images.js";
  let newContent = "const IMAGES = {\n";

  // Loop through the IMAGES object and manually format each key-value pair
  Object.keys(IMAGES).forEach((key) => {
    newContent += `  "${key}": ${IMAGES[key]},\n`; // Directly insert the new URL string
  });

  newContent += "};\n\n";
  newContent += "export default IMAGES;\n";

  fs.writeFile(filePath, newContent, "utf8", (err) => {
    if (err) {
      console.error("Error writing to file:", err);
    } else {
      console.log("images.js updated successfully!");
    }
  });
};

addToImages()
  .then(() => {
    console.log("Images added successfully!");
  })
  .catch((error) => {
    console.error("Error adding images:", error);
  });
