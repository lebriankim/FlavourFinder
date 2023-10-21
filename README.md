[![License](https://img.shields.io/badge/License-Apache2-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0) [![Community](https://img.shields.io/badge/Join-Community-blue)](https://developer.ibm.com/callforcode/solutions/projects/get-started/)

# FlavourFinder
![FlavourFinder](./images/FlavourFinder.png)

- [Project summary](#project-summary)
  - [The issue we are hoping to solve](#the-issue-we-are-hoping-to-solve)
  - [How our technology solution can help](#how-our-technology-solution-can-help)
  - [Our idea](#our-idea)
- [Technology implementation](#technology-implementation)
  - [IBM AI service(s) used](#ibm-ai-services-used)
  - [Solution architecture](#solution-architecture)
- [Presentation materials](#presentation-materials)
  - [Solution demo video](#solution-demo-video)
  - [Project development roadmap](#project-development-roadmap)


## Project summary

### The issue we are hoping to solve

Food waste imposes a great impact on the environment with about 30 to 40% of the food supply within the United States estimated to result to food waste. Food wastes occur at various levels from either the production process or even at a consumer level when consumers buy or cook more food than they need resulting in throwing out extras. Our solution aims to focus on reducing the volume of food wastes particularly within households by encouraging reuse or recycling of foods about to go bad. 


### How our technology solution can help

Our interactive mobile application is designed to analyze images of foods about to go bad, captured and uploaded by users, and subsequently provide recipe suggestions for the specific food items.

### Our idea

Approximately 17% of the global food production is wasted and a substantial portion of the waste originates from households. This greatly impacts the environment as most of the food wastes end up in landfills producing a large amount of greenhouse gases, such as carbon dioxide and methane, thus contributing to global warming and climate change. Furthermore, it leads to a waste of resources such as land, water, labour and energy invested in the production and processing of food that ends up being wasted.

To tackle this issue head-on, our innovative solution is strategically designed to promote the reuse and recycling of foods within households. Our robust approach entails the development of an android application, which interfaces with IBM Watson Assistant- a conversational system designed to engage users in meaningful dialogue. The Assistant will prompt users for images of food that are about to go bad, then a list of food items would be detected based on the uploaded image. Image detection is performed utilizing our trained model, which was derived from the training of a foundational YOLOv8 model (a sophisticated object detection model) on our custom dataset encompassing over 6,000 food images. Upon the classification of food items, a dynamic sequence is triggered, involving an API call to Spoonacular, a food API that allows access to over 5,000 recipes, that would return a list of recipes tailored to the classified food ingredients that users can choose from. 

Remarkably user-friendly, this streamlined process helps tackle food waste problems with the simple act of users having to upload food images and a list of various recipes would be suggested, thus prompting the productive use of foods that would otherwise end up in trash.

Our current implementation is limited as our model was trained on a small set of images causing the detection process to identify a small set of foods. However, our future implementation plans encompasses a substantial expansion of our training dataset so as to make our predictions more accurate and have a variety of food items identified. 

Moreover, we would also like to refine our image detection process by also identifying the freshness levels of foods, thereby prioritizing recipes for food items that are least fresh. 

To further encourage the reduction of food loss and waste, our application goes beyond the realm of individual households. It extends an open hand to users, giving users an opportunity to share surplus food with nearby individuals or contribute to charitable causes, thus directing excess foods toward those in need. Thus encouraging a more sustainable and resource-efficient environment where food waste is highly unlikely.


## Technology implementation

### IBM AI service(s) used

#### [IBM Watson® Assistant](https://cloud.ibm.com/catalog/services/watson-assistant)

We leverage IBM Watson® Assistant to guide users through the process of discovering recipes based on a list of food items or a picture. The user interface of the application is provided through IBM Watson® Assistant's webview<sup>[1]</sup>. The process commences by offering the user a choice between providing a list of food items or a picture. The workflow<sup>[2]</sup> for each option is as follows:

**List of Food Items Flow**

1. The system prompts the user to enter a list of food items.
2. It then constructs and dispatches a request to the Spooncular API using a custom extension.
3. Upon receiving a response, the system informs the user about the number of recipes found.
4. It displays the first recipe and offers options for navigating through the recipes (previous, next), concluding the process (done), or starting a new list of food items flow (new search).

**Picture Flow**

1. The system presents a button that allows users to upload a picture.
2. When the user clicks this button<sup>[3]</sup>, control is transferred to a custom user interface that facilitates image uploading and forwards the image to the Ultralytics API for processing through our Image Detection model. The result is a list of food items.
3. It proceeds to construct and transmit a request to the Spooncular API through a custom extension.
4. After receiving a response, the system informs the user about the number of recipes found.
5. It displays the first recipe and provides options for navigating through the recipes (previous, next), concluding the process (done), or initiating a new picture flow (new search).

We have chosen IBM Watson® Assistant to create a user-friendly, guided experience, ensuring that even first-time users can easily navigate the application. This approach is designed to facilitate user adoption.

> <sup>[1]</sup> The main class of our android application, [MainActivity.kt](https://github.com/lebriankim/FlavourFinder/blob/main/android-app/app/src/main/java/com/example/recipe/MainActivity.kt#L38), renders the webview.
>
> <sup>[2]</sup> The actions of the workflow are defined in [recipe-bot-action-v32.json](https://github.com/lebriankim/FlavourFinder/blob/main/ibm/recipe-bot-action-v32.json)
> 
> <sup>[3]</sup> IBM Watson® Assistant's additional functions are defined in [index.html](https://github.com/lebriankim/FlavourFinder/blob/main/android-app/app/src/main/assets/index.html).


### Solution architecture

Diagram and step-by-step description of the flow of our solution:

![SolutionArchitecture](./images/SolutionArchitecture.png)

1. Watson Assistant prompts the user to upload an image
2. User uploads food image to Android application
3. The application sends the uploaded image to our image detection model through the Ultralytics API for image prediction
4. The model detects food items based on the uploaded image and returns a list of identified food items
5. Watson Assistant then sends the list of identified food items generated to Spoonacular API to generate recipes
6. Watson Assistant displays a list of recipes

Links:
- Image Detection Model (hosted on ultralytics HUB) [FlavourFinder - v2](https://hub.ultralytics.com/models/af6qKxjR7JKNq39pWmPK)
- Image Detection Training Dataset (hosted on ultralytics HUB) [Eggs.v5-custom_data5.yolov8](https://hub.ultralytics.com/datasets/mG8GrG6fJLqUVTOMENZH)
- Spoonacular Food API [https://spoonacular.com/food-api](https://spoonacular.com/food-api)


## Presentation materials

### Solution demo video

[![Watch the video](./images/FlavourFinder-thumbnail.png)](https://www.youtube.com/watch?v=y_LTA07PO-4)

### Project development roadmap

The project currently does the following things.

- Runs on Android.
- Generates recipes based on list of food items entered by user.
- Generates recipes based on picture of food items uploaded by user.
- Generates up to 10 recipes per a request.
- Displays pictures of recipes generated.

In the future we plan to...

See below for our proposed schedule on next steps after Call for Code 2023 submission.

![Roadmap](./images/roadmap.png)
