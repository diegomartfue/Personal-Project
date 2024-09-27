import openai
import pyttsx3
import speech_recognition as sr
import os
from dotenv import load_dotenv

# Load environment variables
load_dotenv()

# OpenAI API Key
#openai.api_key = os.getenv('sk-proj-**********JPQWuQp')

# Function to convert text to speech
def SpeakText(command):
    # Initialize engine
    engine = pyttsx3.init()
    engine.say(command)
    engine.runAndWait()

# Initialize recognizer
r = sr.Recognizer()

def record_text():
    while True:
        try:
            # Use microphone as a source of input
            with sr.Microphone() as source2:
                # Prepare recognizer to receive input
                r.adjust_for_ambient_noise(source2, duration=0.2)
                print("I'm listening")

                # Listens for the user's input
                audio2 = r.listen(source2)

                # Using Google to recognize audio
                MyText = r.recognize_google(audio2)
                return MyText

        except sr.RequestError as e:
            print("Could not request results; {0}".format(e))
        except sr.UnknownValueError:
            print("Unknown error occurred")

def send_to_chatGPT(messages, model="gpt-3.5-turbo"):
    response = openai.ChatCompletion.create(
        model=model,
        messages=messages,
        max_tokens=100, 
        n=1,
        stop=None,
        temperature=0.5,
    )
    message = response.choices[0].message["content"]
    messages.append({"role": "assistant", "content": message})
    return message

messages = []
while True:
    text = record_text()
    messages.append({"role": "user", "content": text})
    response = send_to_chatGPT(messages)
    SpeakText(response)

    print(response)
