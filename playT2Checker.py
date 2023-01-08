import smtplib
from email.mime.text import MIMEText
from urllib.request import urlopen
from html.parser import HTMLParser

class NumberOfPlayersParser(HTMLParser):
    def __init__(self):
        super().__init__()
        self.number_of_players = None

    def handle_starttag(self, tag, attrs):
        if tag == 'strong':
            self.number_of_players = None

    def handle_data(self, data):
        if self.number_of_players is None:
            self.number_of_players = data

# def get_number_of_players():
#     with urlopen("https://www.playt2.com/server.html") as response:
#         html = response.read()
#     parser = NumberOfPlayersParser()
#     parser.feed(html.decode())
#     if parser.number_of_players:
#         return int(parser.number_of_players)
#     else:
#         # Handle the case where the number of players is not available
#         # You can return a default value, or raise an exception
#         return 0  # or raise Exception("Number of players is not available")

def get_number_of_players():
    with urlopen("https://www.playt2.com/server.html") as response:
        html = response.read()
    print(html)  # Add this line to print the html output
    parser = NumberOfPlayersParser()
    parser.feed(html.decode())
    return int(parser.number_of_players.strip())


def send_email(to, subject, body):
    sent_from_user = "jgnotifier@gmail.com"
    sent_from_pass = "dysrgrkleuhkknoe"
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
if number_of_players > 7:
    to = "jesse1819@gmail.com"
    subject = "PlayT2 has " + number_of_players + " players"
    body = "Python script running from AWS"
    send_email(to, subject, body)
