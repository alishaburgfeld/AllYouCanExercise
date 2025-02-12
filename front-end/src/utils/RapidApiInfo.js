import axios from 'axios'

export const exerciseOptions = {
    method: 'GET',
    params: {
        limit: '1000',
      },
    headers: {
      'X-RapidAPI-Host': 'exercisedb.p.rapidapi.com',
      'X-RapidAPI-Key': process.env.REACT_APP_RAPID_API_KEY,
    },
  };

  export const fetchRapidData = async (url, options) => {
    const response = await axios.request(url, options);
    return response.data;
  };

    const getRapidAPIbyTarget = async () => {
        const options = {
            method: 'GET',
            url: 'https://exercisedb.p.rapidapi.com/exercises/target/biceps',
            headers: {
              'x-rapidapi-key': '89b2f4fcc3mshb1c4db4c3ef15afp151f39jsn4718f6fb42de',
              'x-rapidapi-host': 'exercisedb.p.rapidapi.com'
            }
          };
          
          try {
              const response = await axios.request(options);
              console.log(response.data);
          } catch (error) {
              console.error(error);
          }
    }



const getBodyParts = async () => {
    // body parts are (10) ['back', 'cardio', 'chest', 'lower arms', 'lower legs', 'neck', 'shoulders', 'upper arms', 'upper legs', 'waist'

const options = {
method: 'GET',
url: 'https://exercisedb.p.rapidapi.com/exercises/bodyPartList',
headers: {
'x-rapidapi-key': '89b2f4fcc3mshb1c4db4c3ef15afp151f39jsn4718f6fb42de',
'x-rapidapi-host': 'exercisedb.p.rapidapi.com'
}
};

try {
const response = await axios.request(options);
console.log('body parts are', response.data);
} catch (error) {
console.error(error);
}
}


const getTargetList = async () => {
    // target list:
    // 'abductors', 'abs', 'adductors', 'biceps', 'calves', 'cardiovascular system', 'delts', 'forearms', 'glutes', 'hamstrings', 'lats', 'levator scapulae', 'pectorals', 'quads', 'serratus anterior', 'spine', 'traps', 'triceps', 'upper back']
    // delts would be shoulders, most of the seratus would be chest (as well as pectorals), 
    const options = {
        method: 'GET',
        url: 'https://exercisedb.p.rapidapi.com/exercises/targetList',
        headers: {
          'x-rapidapi-key': '89b2f4fcc3mshb1c4db4c3ef15afp151f39jsn4718f6fb42de',
          'x-rapidapi-host': 'exercisedb.p.rapidapi.com'
        }
      };
      
      try {
          const response = await axios.request(options);
          console.log(response.data);
      } catch (error) {
          console.error(error);
      }
}


    const getRapidAPIEquipment = async () => {
        const options = {
            method: 'GET',
            url: 'https://exercisedb.p.rapidapi.com/exercises/equipmentList',
            headers: {
              'x-rapidapi-key': '89b2f4fcc3mshb1c4db4c3ef15afp151f39jsn4718f6fb42de',
              'x-rapidapi-host': 'exercisedb.p.rapidapi.com'
            }
          };
          
          try {
              const response = await axios.request(options);
              console.log(response.data);
          } catch (error) {
              console.error(error);
          }
    }
// Equipment List:
// 28) ['assisted', 'band', 'barbell', 'body weight', 'bosu ball', 'cable', 'dumbbell', 'elliptical machine', 'ez barbell', 'hammer', 'kettlebell', 'leverage machine', 'medicine ball', 'olympic barbell', 'resistance band', 'roller', 'rope', 'skierg machine', 'sled machine', 'smith machine', 'stability ball', 'stationary bike', 'stepmill machine', 'tire', 'trap bar', 'upper body ergometer', 'weighted', 'wheel roller']