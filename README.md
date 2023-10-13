[![License](https://img.shields.io/badge/License-Apache2-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0) [![Community](https://img.shields.io/badge/Join-Community-blue)](https://developer.ibm.com/callforcode/solutions/projects/get-started/)

_INSTRUCTIONS: This GitHub repository serves as a template you can use to create a new project for the [2023 Call for Code Global Challenge](https://developer.ibm.com/callforcode/global-challenge/). Use the **Use this template** button to create a new version of this repository and start entering content for your own Call for Code submission project. Make sure you have [registered for the 2023 Call for Code Global Challenge](https://developer.ibm.com/callforcode/global-challenge/register/) to access resources and full project submission instructions. Remove any "INSTRUCTIONS" sections when you are ready to submit your project._

_New to Git and GitHub? This free online course will get you up to speed quickly: [Getting Started with Git and GitHub](https://www.coursera.org/learn/getting-started-with-git-and-github)_.

# Replace this heading with your team/submission name

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
- [Additional details](#additional-details)
  - [How to run the project](#how-to-run-the-project)
  - [Live demo](#live-demo)
- [About this template](#about-this-template)
  - [Contributing](#contributing)
  - [Versioning](#versioning)
  - [Authors](#authors)
  - [License](#license)
  - [Acknowledgments](#acknowledgments)

_INSTRUCTIONS: Complete all required deliverable sections below._

## Project summary

### The issue we are hoping to solve

Food waste imposes a great impact on the environment with about 30 to 40% of the food supply within the United States estimated to result to food waste. Food wastes occur at various levels from either the production process or even at a consumer level when consumers buy or cook more food than they need resulting in throwing out extras. Our solution aims to focus on reducing the volume of food wastes particularly within households by encouraging reuse or recycling of foods about to go bad. 


### How our technology solution can help

Our interactive mobile application is designed to analyze images of foods about to go bad, captured and uploaded by users, and subsequently provide recipe suggestions for the specific food items.

### Our idea

Approximately 17% of the global food production is wasted and a substantial portion of the waste originates from households. This greatly impacts the environment as most of the food wastes end up in landfills producing a large amount of greenhouse gases, such as carbon dioxide and methane, thus contributing to global warming and climate change. Furthermore, it leads to a waste of resources such as land, water, labour and energy invested in the production and processing of food that ends up being wasted.
To tackle this issue head-on, our innovative solution is strategically designed to promote the reuse and recycling of foods within households. Our robust approach entails the development of an android application using React, which interfaces with IBM Watson Assistant- a conversational system designed to engage users in meaningful dialogue. The Assistant will prompt users for images of food that are about to go bad, then a list of food items would be detected based on the uploaded image. The classification of images is done using our trained model that was obtained through training our own custom dataset of foods using YOLOv8, an advanced object detection model. Upon the classification of food items, a dynamic sequence is triggered, involving an API call to Spoonacular, a food API that allows access to over 380,000 recipes, that would return a list of recipes tailored to the classified food ingredients that users can choose from. 
Remarkably user-friendly, this streamlined process helps tackle food waste problems with the simple act of users having to upload food images and a list of various recipes would be suggested, thus prompting the productive use of foods that would otherwise end up in trash.
Our current implementation is limited as our model was trained on a small set of images causing the detection process to identify a small set of foods. However, our future implementation plans encompasses a substantial expansion of our training dataset so as to make our predictions more accurate and have a variety of food items identified. 
Moreover, we would also like to refine our image detection process by also identifying the freshness levels of foods, thereby prioritizing recipes for food items that are least fresh. 
To further encourage the reduction of food loss and waste, our application goes beyond the realm of individual households. It extends an open hand to users, giving users an opportunity to share surplus food with nearby individuals or contribute to charitable causes, thus directing excess foods toward those in need. Thus encouraging a more sustainable and resource-efficient environment where food wastes is highly unlikely.


More detail is available in our [description document](./docs/DESCRIPTION.md).

## Technology implementation

### IBM AI service(s) used

- [Watson Assistant](https://cloud.ibm.com/catalog/services/watson-assistant) - The Watson Assistant is integrated within our android application serving as a conversational system with the users. Through an interactive dialogue conversation, the assistant will prompt the user to upload food images. The uploaded images would then be sent to YOLO API for image prediction and this would return a list of ingredients as identified from the image. Then the list of ingredients is sent to spoonacular API which would return a list of recipes based on the ingredients. The list of recipes would then be displayed in the application as suggestions to which the user can pick any of the recipe they are interested in.


### Solution architecture

Diagram and step-by-step description of the flow of our solution:

![Solution Architecture](./images/Solution_Architecture.png)

1. Watson Assistant prompts the user to upload an image
2. User uploads food image to Android application
3. The application sends the uploaded image to our image detection model through the Ultralytics API for image prediction
4. The model detects food items based on the uploaded image and returns a list of identified food items
5. Watson Assistant then sends the list of identified food items generated to Spoonacular API to generate recipes
6. Watson Assistant displays a list of recipes

## Presentation materials

_INSTRUCTIONS: The following deliverables should be officially posted to your My Team > Submissions section of the [Call for Code Global Challenge resources site](https://cfc-prod.skillsnetwork.site/), but you can also include them here for completeness. Replace the examples seen here with your own deliverable links._

### Solution demo video

[![Watch the video](https://raw.githubusercontent.com/Liquid-Prep/Liquid-Prep/main/images/readme/IBM-interview-video-image.png)](https://youtu.be/vOgCOoy_Bx0)

### Project development roadmap

The project currently does the following things.

- Feature 1
- Feature 2
- Feature 3

In the future we plan to...

See below for our proposed schedule on next steps after Call for Code 2023 submission.

![Roadmap](./images/roadmap.jpg)

## Additional details

_INSTRUCTIONS: The following deliverables are suggested, but **optional**. Additional details like this can help the judges better review your solution. Remove any sections you are not using._

### How to run the project

INSTRUCTIONS: In this section you add the instructions to run your project on your local machine for development and testing purposes. You can also add instructions on how to deploy the project in production.

### Live demo

You can find a running system to test at...

See our [description document](./docs/DESCRIPTION.md) for log in credentials.

---

_INSTRUCTIONS: You can remove the below section from your specific project README._

## About this template

### Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

### Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags).

### Authors

<a href="https://github.com/Call-for-Code/Project-Sample/graphs/contributors">
  <img src="https://contributors-img.web.app/image?repo=Call-for-Code/Project-Sample" />
</a>

- **Billie Thompson** - _Initial work_ - [PurpleBooth](https://github.com/PurpleBooth)

### License

This project is licensed under the Apache 2 License - see the [LICENSE](LICENSE) file for details.

### Acknowledgments

- Based on [Billie Thompson's README template](https://gist.github.com/PurpleBooth/109311bb0361f32d87a2).
