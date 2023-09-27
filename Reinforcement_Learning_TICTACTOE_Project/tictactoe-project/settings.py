import os
from pathlib import Path

DIR_PATH = Path(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

PLAYERS_DATA_TEST_PATH = os.path.join(DIR_PATH,"tictactoe-main", "players")