import axios from "axios";
import Cookies from "js-cookie";
import fs from "fs";

const IMAGES = {
  "Abduction Machine": new URL(
    "./ABDUCTORS/Abduction Machine.png",
    import.meta.url,
  ).href,
};

const getExercises = async () => {
  const csrfToken = Cookies.get("XSRF-TOKEN");
  try {
    const response = await axios.get(
      "http://localhost:8080/api/exercises",
      {
        headers: {
          "X-XSRF-TOKEN": csrfToken,
        },
        withCredentials: true,
      },
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching exercises:", error);
    return [];
  }
};

const addToImages = async () => {
  const exercises = await getExercises(); // Wait for the data from getExercises

  exercises.forEach((exercise) => {
    if (!IMAGES[exercise.name]) {
      IMAGES[
        exercise.name
      ] = `new URL('./${exercise.exerciseGroup}/${exercise.name}.png', import.meta.url).href`;
      // console.log(IMAGES[exercise.name]);
    } else {
      console.log(exercise.name, "already in images");
    }
  });

  updateImagesFile();
};

const updateImagesFile = () => {
  const filePath = "./src/assets/images/images.js";
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

//     // if (!IMAGES[exercise.name]) {
//     IMAGES[
//       exercise.name
//     ] = `new URL('./${exercise.group}/${exercise.name}', import.meta.url).href`;
//     // } else {
//     //   console.log(`${exercise.name} already in images`);
