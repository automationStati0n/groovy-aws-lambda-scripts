import requests
from bs4 import BeautifulSoup
import smtplib
from email.mime.text import MIMEText

def get_number_of_players():
    # Use requests to get the webpage HTML
    page = requests.get("https://www.playt2.com/server.html")
    # Use BeautifulSoup to parse the HTML
    soup = BeautifulSoup(page.content, 'html.parser')
    # Find the element with the desired style attribute
    number_of_players = soup.find(name='strong')
    return number_of_players.string

def send_email(to, subject, body):
    sent_from_user = "jgnotifier@gmail.com"
    sent_from_pass = "dysrgrkleuhkknoe"
    # Use smtplib to send the email
    server = smtplib.SMTP('smtp.gmail.com', 587)
    server.starttls()
    server.login(sent_from_user, sent_from_pass)
    msg = MIMEText(body)
    msg['Subject'] = subject
    msg['From'] = sent_from_user
    msg['To'] = to
    server.sendmail(sent_from_user, to, msg.as_string())
    server.quit()

number_of_players = get_number_of_players()
if int(number_of_players) > 7:
    to = "jesse1819@gmail.com"
    subject = "PlayT2 has " + number_of_players + " players"
    body = "Python script running from AWS"
    send_email(to, subject, body)