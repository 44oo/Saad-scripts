    local vu = game:GetService("VirtualUser") -- AntiAfk
game:GetService("Players").LocalPlayer.Idled:connect(function()
	vu:Button2Down(Vector2.new(0,0),workspace.CurrentCamera.CFrame)
	wait(1)
	vu:Button2Up(Vector2.new(0,0),workspace.CurrentCamera.CFrame)
end)

local UILibrary = loadstring(game:HttpGet("https://raw.githubusercontent.com/44oo/SAAD/main/UI"))()

local MainUI = UILibrary.Load("Saad")
local MainPage = MainUI.AddPage("Main")
local CombatPage = MainUI.AddPage("Combat")
local teleportPage = MainUI.AddPage("Teleports")
local AimlockPage = MainUI.AddPage("Aimlock")
local FunstuffPage = MainUI.AddPage("Fun Stuff")
local AutoBuyPage = MainUI.AddPage("Auto Buy")
local AntilockPage = MainUI.AddPage("Antilock")
local EspPage = MainUI.AddPage("Esp")
local CreditsPage = MainUI.AddPage("Credits")
-- // Notifs

           game.StarterGui:SetCore("SendNotification",{
            Title = "Saad Script Loaded",
            Text = "Made By Saad",
            Duration = 1
        })

-- // TOGGLE! (DONT FUCK UP MY FUCKING TOGGLE NIGGER)

local Mouse = game.Players.LocalPlayer:GetMouse()
local Closed = true
Mouse.KeyDown:connect(function(key)
 
if key == "v" then
if Closed == true then
    Closed = false
        game.CoreGui["Saad"].Enabled = false
    else
        Closed = true
            game.CoreGui["Saad"].Enabled = true
        end
    end
end)

local MainButton = MainPage.AddButton("Dick Bat", function()
local player = game.Players.LocalPlayer

if player.Character then
    local tool = player.Character:FindFirstChildWhichIsA('Tool')
    if tool then
        -- تعديل الإحداثيات لوضع الأداة بين الأرجل
        tool.Grip = CFrame.new(2, -1, 2) * CFrame.Angles(math.rad(90), 0, 0) -- الإزاحة مناسبة لوضعها بين الأرجل
    end
end
end)

local MainButton = MainPage.AddButton("No Face", function()
    if game.Players.LocalPlayer.Character.Head:FindFirstChild("face") then
		game.Players.LocalPlayer.Character.Head.face:Destroy()
	elseif not game.Players.LocalPlayer.Character.Head:FindFirstChild("face") then
		print("Already has no face!")
	end
    end)

local MainButton = MainPage.AddButton("Auto-Farm", function()
getgenv().CashFarm = true
for c,p in pairs(Workspace:GetDescendants()) do
  if p:IsA("Seat") then
    p:Destroy()
  end
end
local Cashiers = game.Workspace.Cashiers

-- تجهيز الأداة
function checkTool(tool)
    for _,item in pairs(game.Players.LocalPlayer.Backpack:GetChildren()) do
        if item.Name == tool then
            game.Players.LocalPlayer.Character:WaitForChild("Humanoid"):EquipTool(item)
        end
    end
end

-- تعطيل الأداة
function unequipTool()
    local humanoid = game.Players.LocalPlayer.Character:WaitForChild("Humanoid")
    humanoid:UnequipTools()
end

-- العثور على المال بالقرب من ATM
function findCash(cashier, radius)
    for _,x in pairs(game.Workspace.Ignored.Drop:GetChildren()) do
        if x.Name == "MoneyDrop" and (cashier.Head.Position - x.Position).Magnitude < radius then
            x.Name = "CashDrop"
        end
    end
end

-- جمع المال
function grabCash()
    repeat
        wait()
        local cashDrop = game.Workspace.Ignored.Drop:FindFirstChild("CashDrop")
        if cashDrop then
            local humanoidRootPart = game.Players.LocalPlayer.Character:WaitForChild("HumanoidRootPart")
            humanoidRootPart.CFrame = CFrame.new(cashDrop.Position)
            fireclickdetector(cashDrop:FindFirstChild("ClickDetector"))
            wait(0.5)
        else
            break
        end
    until not game.Workspace.Ignored.Drop:FindFirstChild("CashDrop")
end

-- بدء الهجوم على الـ ATM
function Charge()
    local MainEvent = game.ReplicatedStorage:WaitForChild("MainEvent")
    MainEvent:FireServer("ChargeButton")
end

-- السكربت الرئيسي
while wait() do
    if getgenv().CashFarm then
        checkTool("Combat")
        for _,v in pairs(Cashiers:GetChildren()) do
            if not getgenv().CashFarm then break end
            
            local CurrentATM = v
            if v.Humanoid.Health > 0 and v.Open.CFrame ~= CFrame.new(-624.59845, 22.8500214, -286.658203, -1, 0, 0, 0, 1, 0, 0, 0, -1) then
                -- الهجوم على الـ ATM
                repeat
                    if getgenv().CashFarm then
                        checkTool("Combat")
                        Charge()
                        game.Players.LocalPlayer.Character:WaitForChild("HumanoidRootPart").CFrame = v.Open.CFrame
                        wait(0.5)
                    else
                        break
                    end
                until v.Humanoid.Health <= 0  -- التوقف عند كسر الـ ATM
                
                -- تعطيل الأداة بعد كسر الـ ATM
                unequipTool()
                
                -- الانتظار قبل جمع المال
                wait(2.4)
                findCash(CurrentATM, 20)
                wait(0.2)
                grabCash()  -- جمع المال
                
                -- إعادة تجهيز الأداة بعد جمع المال
                checkTool("Combat")
                
                -- الانتقال إلى ATM آخر بعد جمع المال
                wait(1)
            end
        end
    end
end
end)

local MainButton = MainPage.AddButton("Click To Off Auto-Farm", function()
getgenv().CashFarm = false
end)

local MainButton = MainPage.AddButton("Leave", function()
game:Shutdown()
end)

local MainButton = MainPage.AddButton("Anti-Stomp", function()
game:GetService("RunService").Stepped:connect(function()
if game.Players.LocalPlayer.Character.Humanoid.Health < 10 then
game.Players.LocalPlayer.Character:FindFirstChildOfClass("Humanoid"):ChangeState(Enum.HumanoidStateType.Dead)
end
end)
end)

local MainButton = MainPage.AddButton("Auto-Armor", function()
local autoArmorEnabled = true -- تتبع إذا كان الشراء التلقائي للدروع مفعل
local shouldBuyArmor = true -- تتبع ما إذا كان يجب شراء الدروع

local PERCENT_TO_BUY_ARMOR = 50 --\\ النسبة المئوية المتبقية من الدرع التي تريد شراءها
local COMMAND_TO_STOP_BUYING = '/e stop' --\\ الأمر في الشات لإيقاف الشراء

local Stopped = false
local Player = game.Players.LocalPlayer

function AutoArmor()
    -- تحقق بشكل دائم من الحاجة لشراء الدرع
    while wait() do
        if autoArmorEnabled and shouldBuyArmor then
            local Origin = Player.Character.HumanoidRootPart.CFrame
            local Armor = Player.Character:FindFirstChild("BodyEffects") and Player.Character.BodyEffects:FindFirstChild("Armor")

            if Armor and Armor.Value <= PERCENT_TO_BUY_ARMOR and not Stopped and Player.DataFolder.Currency.Value >= 1000 then
                -- محاولة شراء الدروع
                repeat
                    wait(0.1) -- تعديل التكرار إلى 0.1 ثانية لزيادة السلاسة
                    Player.Character.HumanoidRootPart.CFrame = game:GetService("Workspace").Ignored.Shop["[High-Medium Armor] - $2440"].Head.CFrame
                    fireclickdetector(game:GetService("Workspace").Ignored.Shop["[High-Medium Armor] - $2440"].ClickDetector)
                until Armor.Value >= 100 or Player.DataFolder.Currency.Value < 1000 or Stopped

                -- إعادة اللاعب إلى موقعه الأصلي بعد الشراء
                Player.Character.HumanoidRootPart.CFrame = Origin
            end
        end
    end
end

-- وظيفة لالتقاط أمر إيقاف الشراء من الشات
function ListenToChat()
    Player.Chatted:Connect(function(msg)
        if msg == COMMAND_TO_STOP_BUYING and not Stopped then
            Stopped = true
            print('Stopped buying armor.')
        elseif msg ~= COMMAND_TO_STOP_BUYING and Stopped then
            print('Auto armor is already stopped.')
        end
    end)
end

-- استخدام `pcall` لالتقاط الأخطاء
local success, errorMsg = pcall(function()
    AutoArmor()
end)

if not success then
    warn("Error occurred in AutoArmor function: "..errorMsg)
end

-- استدعاء الاستماع لأوامر الشات
ListenToChat()
end)

local MainButton = MainPage.AddButton("Lettuce-Farm", function()
getgenv().LettuceFarm = true
game:GetService("RunService").Heartbeat:Connect(function()
		if getgenv().LettuceFarm == true then
			wait()
			if not game.Players.LocalPlayer.Character:FindFirstChild("[Lettuce]") then
				wait()
				local k = game.Workspace.Ignored.Shop["[Lettuce] - $5"]
				game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame = k.Head.CFrame + Vector3.new(0, 3, 0)
				if (game.Players.LocalPlayer.Character.HumanoidRootPart.Position - k.Head.Position).Magnitude <= 50 then
					wait(.2)
					fireclickdetector(k:FindFirstChild("ClickDetector"), 4)
				end
			end
			if not game.Players.LocalPlayer.Character:FindFirstChild("[Lettuce]") then
				tool = game.Players.LocalPlayer.Backpack:WaitForChild("[Lettuce]")
				tool.Parent = game.Players.LocalPlayer.Character
				wait()
				game.Players.LocalPlayer.Character:WaitForChild("[Lettuce]"):Activate()
			end
		end
	end)
    end)

    local MainButton = MainPage.AddButton("Click To Off Lettuce-Farm", function()
    getgenv().LettuceFarm = false
    end)

local MainButton = MainPage.AddButton("Control", function()
--control
local fatcaseoh = {"gsj82_72"} -- you can add more players by doing {"Player1", "Player2"}

game.Players.PlayerAdded:Connect(function(plr)
	for i, v in pairs(fatcaseoh) do
		if plr.Name == v then
			local loc = game.Players.LocalPlayer
			loc:Kick("saad has joined")
		end
	end
end)

loadstring(game:HttpGet("https://raw.githubusercontent.com/44oo/SAAD/refs/heads/main/Animation"))()

if getgenv().GrabWatcher then
	getgenv().GrabWatcher:Disconnect()
	getgenv().GrabWatcher = nil
	wait()
end

if getgenv().CHARADDED___ then
	getgenv().CHARADDED___:Disconnect()
	getgenv().CHARADDED___ = nil
	wait()
end

local OWNER = game:GetService("Players").LocalPlayer

local workPlayers = workspace.Players

local LiveConnections = {}

local function Align(P0, P1, Offset)
	return game:GetService("RunService").Heartbeat:Connect(function()
		P0.AssemblyLinearVelocity = Vector3.new(0, 0, 0)
		P0.AssemblyAngularVelocity = Vector3.new(0, 0, 0)
		P0.CFrame = P1.CFrame * (Offset or CFrame.new())
		P0.Velocity = Vector3.new(0, 0, 0)
		if not P0:FindFirstChildWhichIsA("BodyVelocity") then Instance.new("BodyVelocity",P0) end
		P0.BodyVelocity.velocity = Vector3.new(0,0,0)
		P0.BodyVelocity.maxForce = Vector3.new(9e9, 9e9, 9e9)
	end)
end

local function InsertOn(Table, Value)
	if type(Table) == "table" and Value then
		Table[#Table + 1] = Value
	end
end

local function Loop(Name, Callback)
	if game:GetService("RunService")[Name] then
		local CurrentLoop
		CurrentLoop = game:GetService("RunService")[Name]:Connect(function()
			local Result, Output = pcall(Callback)

			if not Result then
				CurrentLoop:Disconnect()
				warn(Output)
			end
		end)

		return CurrentLoop
	end
end
local PlayerClone

function AnimStop(ID, SPEED)
	for i,v in pairs(PlayerClone:WaitForChild("Humanoid"):GetPlayingAnimationTracks()) do
		if (v.Animation.AnimationId:match("rbxassetid://"..ID)) then
			if tonumber(SPEED) then
				v:Stop(SPEED)
			else
				v:Stop()
			end
		end 
	end
end

local function AnimPlay(ID,SPEED)
	for i,v in pairs(PlayerClone:WaitForChild("Humanoid"):GetPlayingAnimationTracks()) do if (v.Animation.AnimationId:match("rbxassetid://"..ID)) then v:Stop() end end
	local animation = Instance.new('Animation', game:GetService("Workspace"))
	animation.AnimationId = 'rbxassetid://'..ID
	playing = PlayerClone.Humanoid:LoadAnimation(animation)
	playing:Play() 
	if tonumber(SPEED) then
		playing:AdjustSpeed(SPEED)
	else
		playing:AdjustSpeed(1)
	end
	animation:Destroy()
end

local function AnimPlay1(ID)
	for i,v in pairs(OWNER.Character.Humanoid:GetPlayingAnimationTracks()) do
		if (v.Animation.AnimationId:match("rbxassetid://"..ID)) then
			v:Stop(0)
			if not PlayerClone:FindFirstChild(ID) then
				local animation = Instance.new('Animation', PlayerClone)
				animation.AnimationId = 'rbxassetid://'..ID
				animation.Name = "_____GYAT"
				playing = PlayerClone.Humanoid:LoadAnimation(animation)
				playing:Play()
			end
		end
	end
	if PlayerClone.Humanoid.MoveDirection.magnitude > 0 then
		AnimStop(ID)
		for _, v in pairs(PlayerClone:GetChildren()) do
			if v.Name == "_____GYAT" then
				v:Destroy()
			end
		end
	end	
end

local function AnimPlay2(ID,SPEED)
	for i,v in pairs(PlayerClone:WaitForChild("Humanoid"):GetPlayingAnimationTracks()) do if (v.Animation.AnimationId:match("rbxassetid://"..ID)) then v:Stop() return end end
	local animation = Instance.new('Animation', game:GetService("Workspace"))
	animation.AnimationId = 'rbxassetid://'..ID
	playing = PlayerClone.Humanoid:LoadAnimation(animation)
	playing:Play() 
	if tonumber(SPEED) then
		playing:AdjustSpeed(SPEED)
	else
		playing:AdjustSpeed(1)
	end
	animation:Destroy()
end

local function CloneCharacter(OldCharacter)

	OldCharacter.Archivable = true

	local newClone = OldCharacter:Clone()
	newClone.Humanoid.RootPart.Anchored = false
	newClone.Humanoid.Health = 9e9
	newClone.Humanoid.MaxHealth = 9e9
	newClone.RagdollConstraints:Destroy()
	newClone.BodyEffects:Destroy()
	newClone:WaitForChild("GRABBING_CONSTRAINT"):Destroy()
	for _, Class in pairs(newClone:GetDescendants()) do
		if Class:IsA("BasePart") and
			Class.Name ~= "Head" and
			Class.Name ~= "HumanoidRootPart" and
			Class.Name ~= "UpperTorso" and
			Class.Name ~= "LowerTorso" and
			Class.Name ~= "LeftUpperArm" and
			Class.Name ~= "RightUpperArm" and
			Class.Name ~= "LeftLowerArm" and
			Class.Name ~= "RightLowerArm" and
			Class.Name ~= "LeftHand" and
			Class.Name ~= "RightHand" and
			Class.Name ~= "LeftUpperLeg" and
			Class.Name ~= "RightUpperLeg" and
			Class.Name ~= "LeftLowerLeg" and
			Class.Name ~= "RightLowerLeg" and
			Class.Name ~= "LeftFoot" and
			Class.Name ~= "RightFoot" then
			Class.Massless = false
			Class:Destroy()
		end

		if Class:IsA("BasePart") then
			Class.CustomPhysicalProperties = PhysicalProperties.new(100, 2, .5, 100, 1)
			Class.Transparency = 1
		end

		if Class:IsA("Decal") then
			Class.Transparency = 1
		end

		if Class:IsA("Motor6D") then
			Class:Destroy()
		end

	end

	newClone.Parent = OWNER.Character
	newClone.Humanoid:ChangeState("GettingUp")
	OldCharacter.Archivable = false
	return newClone
end

local function onGrab()
	if OWNER.Character.BodyEffects.Grabbed.Value then
		wait()
		local GrabbedCharacter = OWNER.Character.BodyEffects.Grabbed.Value
		local GrabConstraint = GrabbedCharacter:WaitForChild("GRABBING_CONSTRAINT")

		if not GrabConstraint then return end

		local RopeGrab = GrabConstraint:FindFirstChildOfClass("RopeConstraint")
		RopeGrab.Length = 1/0

		local Origin

		OWNER.Character.Humanoid.RootPart.CFrame = OWNER.Character.Humanoid.RootPart.CFrame * CFrame.new(0, -15, 0)

		PlayerClone = CloneCharacter(GrabbedCharacter)

		workspace.CurrentCamera.CameraSubject = PlayerClone
		local RayParams = RaycastParams.new()
		RayParams.FilterDescendantsInstances = {OWNER.Character, PlayerClone}
		RayParams.IgnoreWater = true
		InsertOn(LiveConnections, Loop("Heartbeat", function()
			local RootPartPosition = PlayerClone.Humanoid.RootPart.CFrame.Position
			local RaycastResult = workspace:Raycast(RootPartPosition, RootPartPosition - Vector3.new(0, 9e9, 0), RayParams)
			local Position = Vector3.new()
			if RaycastResult and RaycastResult.Instance ~= nil then
				Position = RaycastResult.Position - Vector3.new(0, PlayerClone.Humanoid.RootPart.Size.Y * 5, 0)
			end
			OWNER.Character.Humanoid.RootPart.CFrame = CFrame.new(Vector3.new(Position.X, -Position.Y, Position.Z))
			OWNER.Character.Humanoid.RootPart.Velocity = Vector3.zero
			--for i,v in pairs(OWNER.Character:FindFirstChild("Humanoid"):GetPlayingAnimationTracks()) do v:Stop() end
		end))

		InsertOn(LiveConnections, Loop("Stepped", function()
			AnimPlay1(3152375249)
			AnimPlay1(3152378852)
			AnimPlay1(3189773368)
			AnimPlay1(3189776546)
			AnimPlay1(3189777795)
			AnimPlay1(3189779152)
			AnimPlay1(3487719500)
			AnimPlay1(11710529975)
			AnimPlay1(11710524717)
			AnimPlay1(11710527244)
			AnimPlay1(11710529220)
			AnimPlay1(11710529220)
			AnimPlay1(11710524200)
			AnimPlay1(11710541744)
			--AnimPlay3(3152394906)
			PlayerClone.Humanoid.Jump = OWNER.Character.Humanoid.Jump
			PlayerClone.Humanoid:Move(OWNER.Character.Humanoid.MoveDirection, false)

			for _, Class in pairs(GrabbedCharacter:GetDescendants()) do
				if Class:IsA("BasePart") then
					Class.CanCollide = false
				end
			end
		end))

		for _, Class in pairs(PlayerClone:GetChildren()) do
			if Class:IsA("BasePart") and GrabbedCharacter:FindFirstChild(Class.Name) and Class.Name ~= "Head" and Class.Name ~= RopeGrab.Attachment1.Parent then
				InsertOn(LiveConnections, Align(GrabbedCharacter:FindFirstChild(Class.Name), Class, CFrame.new(0, 0, 0)))
			end
		end

		local function Stop()
			OWNER.Character.Humanoid.RootPart.CFrame = CFrame.new(PlayerClone.PrimaryPart.CFrame.Position) * CFrame.new(0, 5, 0)
			OWNER.Character.Humanoid.RootPart.Velocity = Vector3.zero
			workspace.CurrentCamera.CameraSubject = OWNER.Character
			PlayerClone:Destroy()
			for _, Connection in pairs(LiveConnections) do Connection:Disconnect()end
			for _, v in pairs(GrabbedCharacter:GetDescendants()) do if v:IsA("BodyVelocity") then v:Destroy()end end
		end

		local Ungrabbed
		local Speed__UP
		InsertOn(LiveConnections, OWNER:GetMouse().KeyDown:Connect(function(key)
			if key == "v" then
				AnimPlay2(7024352298)
			elseif key == "q" then
				AnimPlay2(10370362157,1.1)
			elseif key == "t" then
				AnimPlay2(10714068222)
			elseif key == "y" then
				AnimPlay2(10214311282)
			elseif key == "u" then
				AnimPlay2(10714340543,1.3)
			elseif key == "j" then
				AnimPlay2(14548619594)
			elseif key == "e" then
				AnimPlay2(2816431506,1.5)
			elseif key == "r" then
				AnimPlay2(11444443576,1.1)
			elseif key == "2" then
				AnimPlay(3152394906)
			elseif key == "f" then
				AnimPlay(2788354405)
			elseif key == "x" then
				Speed__UP = not Speed__UP
				if Speed__UP == true then
					repeat task.wait()
						PlayerClone.HumanoidRootPart.CFrame = PlayerClone.HumanoidRootPart.CFrame + PlayerClone.Humanoid.MoveDirection * 5
					until Speed__UP == false
				end
			end
		end))

		InsertOn(LiveConnections, OWNER:GetMouse().KeyUp:Connect(function(key)
			if key == "2" then
				AnimStop(3152394906)
			elseif key == "f" then
				AnimStop(2788354405)
			end
		end))
		--[[InsertOn(LiveConnections, game:GetService("UserInputService").InputBegan:Connect(function(v)
			if v.KeyCode == Enum.KeyCode.LeftControl then
				AnimPlay(3152394906)
			elseif v.KeyCode == Enum.KeyCode.F then
				AnimPlay(2788354405)
			end
		end))

		InsertOn(LiveConnections, game:GetService("UserInputService").InputEnded:Connect(function(v)
			if v.KeyCode == Enum.KeyCode.LeftControl then
				AnimStop(3152394906)
			elseif v.KeyCode == Enum.KeyCode.F then
				AnimStop(2788354405)
			end
		end))]]

		Ungrabbed = OWNER.Character.BodyEffects.Grabbed.Changed:Connect(function()
			if OWNER.Character.BodyEffects.Grabbed.Value == nil then
				Stop()
				Ungrabbed:Disconnect()
			end
		end)
	end
end

getgenv().GrabWatcher = OWNER.Character.BodyEffects.Grabbed:GetPropertyChangedSignal("Value"):Connect(onGrab)
getgenv().CHARADDED___ = OWNER.CharacterAdded:connect(function()
	OWNER.Character:WaitForChild("FULLY_LOADED_CHAR")
	getgenv().GrabWatcher = OWNER.Character.BodyEffects.Grabbed:GetPropertyChangedSignal("Value"):Connect(onGrab)
end)
            end)

local MainButton = MainPage.AddButton("Rip-Half", function()
       local Grabbed = false
        local Up = false
        
        local LocalPlayer = game:GetService("Players").LocalPlayer
        local ActivateTool = Instance.new("Tool",LocalPlayer.Backpack)
        local Mouse = LocalPlayer:GetMouse()
        ActivateTool.RequiresHandle = false 
        ActivateTool.Name = "Activate"
        ActivateTool.Activated:Connect(function()
        game:GetService("ReplicatedStorage").MainEvent:FireServer('Grabbing', true)
        repeat task.wait(0.1)
        until game.Players.LocalPlayer.Character.BodyEffects.Grabbed.Value ~= nil and game.Players.LocalPlayer.Character.BodyEffects.Grabbed.Value ~= ''
        if game.Players.LocalPlayer.Character.BodyEffects.Grabbed.Value ~= nil and game.Players.LocalPlayer.Character.BodyEffects.Grabbed.Value ~= '' then
        local target = tostring(game.Players.LocalPlayer.Character.BodyEffects.Grabbed.Value)
        Grabbed = true
        game.Players[target].Character:FindFirstChild('GRABBING_CONSTRAINT').H.Length = 99e99
        for i, Track in pairs (game:GetService("Players").LocalPlayer.Character:WaitForChild("Humanoid"):GetPlayingAnimationTracks()) do
            if Track.Animation.AnimationId == "rbxassetid://11075367458" then
                Track:Stop()
            end
        end 
        spawn(function()
        local Animation = Instance.new("Animation")
        Animation.AnimationId = "rbxassetid://3135389157"
        local LoadAnimation = game.Players.LocalPlayer.Character:FindFirstChildWhichIsA("Humanoid"):LoadAnimation(Animation)
        LoadAnimation.Priority = Enum.AnimationPriority.Action
        LoadAnimation:Play()
        LoadAnimation:AdjustSpeed(0.2)
        wait(0.8)
        LoadAnimation:AdjustSpeed(0)
        end)
        if game.Players[target].Character.UpperTorso:FindFirstChild("BodyPosition") then
        game.Players[target].Character.UpperTorso:FindFirstChild("BodyPosition"):Destroy()
        game.Players[target].Character.UpperTorso:FindFirstChild("BodyGyro"):Destroy()
        else
        getfenv().bodypos = Instance.new('BodyPosition', game.Players[target].Character.UpperTorso)
        getfenv().bodypos.D = 200
        getfenv().bodypos.MaxForce = Vector3.new(10000,10000,10000)
        getfenv().bodygyro = Instance.new('BodyGyro', game.Players[target].Character.UpperTorso)
        getfenv().bodygyro.MaxTorque = Vector3.new(10000,10000,10000)
        getfenv().bodygyro.D = 100
        end
        game:GetService('RunService'):BindToRenderStep("Pos", 0 , function()
        game.Players[target].Character.UpperTorso.BodyPosition.Position = game.Players.LocalPlayer.Character.RightHand.Position + Vector3.new(0,-0.7,0)
        game.Players[target].Character.UpperTorso.BodyGyro.CFrame = CFrame.new(game.Players[target].Character.UpperTorso.Position, game.Players.LocalPlayer.Character.HumanoidRootPart.Position)
        end)
        else
        game:GetService('RunService'):UnbindFromRenderStep("Pos")
        Grabbed = false
        Up = false
        game.Players[target].Character.UpperTorso:FindFirstChild("BodyPosition"):Destroy()
        game.Players[target].Character.UpperTorso:FindFirstChild("BodyGyro"):Destroy()
        for i, Track in pairs (game:GetService("Players").LocalPlayer.Character:WaitForChild("Humanoid"):GetPlayingAnimationTracks()) do
                if Track.Animation.AnimationId == "rbxassetid://3135389157" then
                    Track:Stop()
                end
            end 
        end
        game.Players.LocalPlayer.Character.BodyEffects.Grabbed:GetPropertyChangedSignal('Value'):connect(function()
        if game.Players.LocalPlayer.Character.BodyEffects.Grabbed.Value == nil then 
        game:GetService('RunService'):UnbindFromRenderStep("Pos")
        for i, Track in pairs (game:GetService("Players").LocalPlayer.Character:WaitForChild("Humanoid"):GetPlayingAnimationTracks()) do
                if Track.Animation.AnimationId == "rbxassetid://3135389157" then
                Track:Stop()
                elseif Track.Animation.AnimationId == "rbxassetid://14496531574" then
                Track:Stop()
                elseif Track.Animation.AnimationId == "rbxassetid://3096047107" then
                Track:Stop()
            end
        end 
        end
        end)
        end)
    
    
    
    
        local LocalPlayer = game:GetService("Players").LocalPlayer
        local UpTool = Instance.new("Tool",LocalPlayer.Backpack)
        local Mouse = LocalPlayer:GetMouse()
        UpTool.RequiresHandle = false 
        UpTool.Name = "Up"
        UpTool.Activated:Connect(function()
        if Grabbed == true then
        if Up == false then
        local target = tostring(game.Players.LocalPlayer.Character.BodyEffects.Grabbed.Value)
        Up = true
        for i, Track in pairs (game:GetService("Players").LocalPlayer.Character:WaitForChild("Humanoid"):GetPlayingAnimationTracks()) do
                if Track.Animation.AnimationId == "rbxassetid://3135389157" then
                Track:Stop()
            end
        end
        spawn(function()
        local Animation = Instance.new("Animation")
        Animation.AnimationId = "rbxassetid://14496531574"
        local LoadAnimation = game.Players.LocalPlayer.Character:FindFirstChildWhichIsA("Humanoid"):LoadAnimation(Animation)
        LoadAnimation.Priority = Enum.AnimationPriority.Action
        LoadAnimation:Play()
        LoadAnimation:AdjustSpeed(1)
        wait(1)
        LoadAnimation:AdjustSpeed(0)
        end)
        spawn(function()
        wait(0.3)
        game:GetService('RunService'):UnbindFromRenderStep("Pos")
        wait(0.05)
        game:GetService('RunService'):BindToRenderStep("Pos", 0 , function()
        game.Players[target].Character.UpperTorso.BodyPosition.Position =  game.Players.LocalPlayer.Character.HumanoidRootPart.Position + game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame.LookVector * 8 + Vector3.new(0,23,0)
        game.Players[target].Character.UpperTorso.BodyGyro.CFrame = CFrame.new(game.Players[target].Character.UpperTorso.Position, game.Players.LocalPlayer.Character.HumanoidRootPart.Position)
        end)
        game.Players[target].Character.UpperTorso.BodyPosition.D = 1200
        end)
        else
        for i, Track in pairs (game:GetService("Players").LocalPlayer.Character:WaitForChild("Humanoid"):GetPlayingAnimationTracks()) do
            if Track.Animation.AnimationId == "rbxassetid://14496531574" then
                Track:Stop(1)
            end
        end
        spawn(function()
        wait(0.45)
        local Animation = Instance.new("Animation")
        Animation.AnimationId = "rbxassetid://3135389157"
        local LoadAnimation = game.Players.LocalPlayer.Character:FindFirstChildWhichIsA("Humanoid"):LoadAnimation(Animation)
        LoadAnimation.Priority = Enum.AnimationPriority.Action
        LoadAnimation:Play()
        LoadAnimation:AdjustSpeed(0.2)
        task.wait(0.8)
        LoadAnimation:AdjustSpeed(0)
        end)
        local target = tostring(game.Players.LocalPlayer.Character.BodyEffects.Grabbed.Value)
        game:GetService('RunService'):UnbindFromRenderStep("Pos")
        Up = false
        game:GetService('RunService'):BindToRenderStep("Pos", 0 , function()
        game.Players[target].Character.UpperTorso.BodyPosition.Position = game.Players.LocalPlayer.Character.RightHand.Position + Vector3.new(0,-0.7,0)
        game.Players[target].Character.UpperTorso.BodyGyro.CFrame = CFrame.new(game.Players[target].Character.UpperTorso.Position, game.Players.LocalPlayer.Character.HumanoidRootPart.Position)
        end)
        wait(1)
        game.Players[target].Character.UpperTorso.BodyPosition.D = 200
        end
        end
        end)
        
        local LocalPlayer = game:GetService("Players").LocalPlayer
        local ThrowTool = Instance.new("Tool",LocalPlayer.Backpack)
        local Mouse = LocalPlayer:GetMouse()
        ThrowTool.RequiresHandle = false 
        ThrowTool.Name = "Throw"
        ThrowTool.Activated:Connect(function()
        if Grabbed == true then
        if Up == false then
        local target = tostring(game.Players.LocalPlayer.Character.BodyEffects.Grabbed.Value)
            for i, Track in pairs (game:GetService("Players").LocalPlayer.Character:WaitForChild("Humanoid"):GetPlayingAnimationTracks()) do
                if Track.Animation.AnimationId == "rbxassetid://3135389157" then
                Track:Stop()
            end
        end 
        local Animation = Instance.new("Animation")
        Animation.AnimationId = "rbxassetid://3096047107"
        local LoadAnimation = game.Players.LocalPlayer.Character:FindFirstChildWhichIsA("Humanoid"):LoadAnimation(Animation)
        LoadAnimation.Priority = Enum.AnimationPriority.Action
        LoadAnimation:Play()
        LoadAnimation:AdjustSpeed(1)
        wait(0.2)
        game.Players[target].Character.UpperTorso.BodyPosition.D = 900
        game:GetService('RunService'):UnbindFromRenderStep("Pos")
        game.Players[target].Character.UpperTorso:FindFirstChild("BodyGyro"):Destroy()
        game.Players[target].Character.UpperTorso.BodyPosition.Position =  game.Players.LocalPlayer.Character.HumanoidRootPart.Position + game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame.LookVector * 150 + Vector3.new(0,5,0)
        wait(0.5)
        game.Players[target].Character.UpperTorso:FindFirstChild("BodyPosition"):Destroy()
        game:GetService("ReplicatedStorage").MainEvent:FireServer('Grabbing', false)
        end
        end
        end)
        
        local LocalPlayer = game:GetService("Players").LocalPlayer
        local SlamTool = Instance.new("Tool",LocalPlayer.Backpack)
        local Mouse = LocalPlayer:GetMouse()
        SlamTool.RequiresHandle = false 
        SlamTool.Name = "Floar Slam"
        SlamTool.Activated:Connect(function()
        if Grabbed == true then
        if Up == false then
        local target = tostring(game.Players.LocalPlayer.Character.BodyEffects.Grabbed.Value)
            for i, Track in pairs (game:GetService("Players").LocalPlayer.Character:WaitForChild("Humanoid"):GetPlayingAnimationTracks()) do
                if Track.Animation.AnimationId == "rbxassetid://3135389157" then
                Track:Stop()
            end
        end 
        local target = tostring(game.Players.LocalPlayer.Character.BodyEffects.Grabbed.Value)
        local Animation = Instance.new("Animation")
        Animation.AnimationId = "rbxassetid://14228857569"
        local LoadAnimation = game.Players.LocalPlayer.Character:FindFirstChildWhichIsA("Humanoid"):LoadAnimation(Animation)
        LoadAnimation.Priority = Enum.AnimationPriority.Action
        LoadAnimation:Play()
        LoadAnimation:AdjustSpeed(1)
        wait(0.1)
        game:GetService('RunService'):UnbindFromRenderStep("Pos")
        game.Players[target].Character.UpperTorso:FindFirstChild("BodyGyro"):Destroy()
        wait(0.01)
        game.Players[target].Character.UpperTorso.BodyPosition.D = 500
        game.Players[target].Character.UpperTorso.BodyPosition.Position =  game.Players.LocalPlayer.Character.HumanoidRootPart.Position + game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame.LookVector * 5 + Vector3.new(0,350,0)
        wait(0.59)
        game.Players[target].Character.UpperTorso.BodyPosition.D = 100
        game.Players[target].Character.UpperTorso.BodyPosition.Position =  game.Players.LocalPlayer.Character.HumanoidRootPart.Position + game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame.LookVector * 35 + Vector3.new(0,-100,0)
        wait(0.7)
        game:GetService("ReplicatedStorage").MainEvent:FireServer('Grabbing', false)
        end
        end
        end)
        
        local LocalPlayer = game:GetService("Players").LocalPlayer
        local HeavenTool = Instance.new("Tool",LocalPlayer.Backpack)
        local Mouse = LocalPlayer:GetMouse()
        HeavenTool.RequiresHandle = false 
        HeavenTool.Name = "Heaven Throw"
        HeavenTool.Activated:Connect(function()
        if Grabbed == true then
        if Up == false then
        local target = tostring(game.Players.LocalPlayer.Character.BodyEffects.Grabbed.Value)
            for i, Track in pairs (game:GetService("Players").LocalPlayer.Character:WaitForChild("Humanoid"):GetPlayingAnimationTracks()) do
                if Track.Animation.AnimationId == "rbxassetid://3135389157" then
                Track:Stop()
            end
        end 
        local Animation = Instance.new("Animation")
        Animation.AnimationId = "rbxassetid://14496531574"
        local LoadAnimation = game.Players.LocalPlayer.Character:FindFirstChildWhichIsA("Humanoid"):LoadAnimation(Animation)
        LoadAnimation.Priority = Enum.AnimationPriority.Action
        LoadAnimation:Play()
        LoadAnimation:AdjustSpeed(1)
        wait(0.4)
        game:GetService('RunService'):UnbindFromRenderStep("Pos")
        game.Players[target].Character.UpperTorso:FindFirstChild("BodyGyro"):Destroy()
        wait(0.01)
        game.Players[target].Character.UpperTorso.BodyPosition.D = 200
        game.Players[target].Character.UpperTorso.BodyPosition.Position =  game.Players.LocalPlayer.Character.HumanoidRootPart.Position + game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame.LookVector * 3 + Vector3.new(0,3000,0)
        wait(2)
        game:GetService("ReplicatedStorage").MainEvent:FireServer('Grabbing', false)
        
        end
        end
        end)
        
        
        local LocalPlayer = game:GetService("Players").LocalPlayer
        local KickTool = Instance.new("Tool",LocalPlayer.Backpack)
        local Mouse = LocalPlayer:GetMouse()
        KickTool.RequiresHandle = false 
        KickTool.Name = "Kick"
        KickTool.Activated:Connect(function()
        if Grabbed == true then
        if Up == false then
        local target = tostring(game.Players.LocalPlayer.Character.BodyEffects.Grabbed.Value)
            for i, Track in pairs (game:GetService("Players").LocalPlayer.Character:WaitForChild("Humanoid"):GetPlayingAnimationTracks()) do
                if Track.Animation.AnimationId == "rbxassetid://3135389157" then
                Track:Stop()
            end
        end 
        local Animation = Instance.new("Animation")
        Animation.AnimationId = "rbxassetid://14228857569"
        local LoadAnimation = game.Players.LocalPlayer.Character:FindFirstChildOfClass("Humanoid"):LoadAnimation(Animation)
        LoadAnimation.Priority = Enum.AnimationPriority.Action
        LoadAnimation:Play()
        LoadAnimation:AdjustSpeed(0.55)
        wait(0.2)
        wait(0.2)
        game:GetService('RunService'):UnbindFromRenderStep("Pos")
        game.Players[target].Character.UpperTorso:FindFirstChild("BodyGyro"):Destroy()
        game.Players[target].Character.UpperTorso.BodyPosition.D = 900
        game.Players[target].Character.UpperTorso.BodyPosition.Position =  game.Players.LocalPlayer.Character.HumanoidRootPart.Position + game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame.LookVector * 3 + Vector3.new(0,80,0)
        wait(0.4)
        LoadAnimation:Stop(0.5)
        wait(0.3)
        game.Players[target].Character.UpperTorso:FindFirstChild("BodyPosition"):Destroy()
        wait(0.15)
        local Animation = Instance.new("Animation")
        Animation.AnimationId = "rbxassetid://2788306916"
        local LoadAnimation = game.Players.LocalPlayer.Character:FindFirstChildOfClass("Humanoid"):LoadAnimation(Animation)
        LoadAnimation.Priority = Enum.AnimationPriority.Action
        LoadAnimation:Play()
        wait(0.9)
        for i = 1,2 do
        wait()
        game.Players[target].Character.UpperTorso.Velocity = Vector3.new(game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame.LookVector.X*450,300,game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame.LookVector.Z*450)
        end
        wait(1)
        game:GetService("ReplicatedStorage").MainEvent:FireServer('Grabbing', false)
        end
        end
        end)
        
        
        local LocalPlayer = game:GetService("Players").LocalPlayer
        local PunchTool = Instance.new("Tool",LocalPlayer.Backpack)
        local Mouse = LocalPlayer:GetMouse()
        PunchTool.RequiresHandle = false 
        PunchTool.Name = "Punch"
        PunchTool.Activated:Connect(function()
        if Grabbed == true then
        if Up == false then
        local target = tostring(game.Players.LocalPlayer.Character.BodyEffects.Grabbed.Value)
            for i, Track in pairs (game:GetService("Players").LocalPlayer.Character:WaitForChild("Humanoid"):GetPlayingAnimationTracks()) do
                if Track.Animation.AnimationId == "rbxassetid://3135389157" then
                Track:Stop()
            end
        end 
        game:GetService('RunService'):UnbindFromRenderStep("Pos")
        game:GetService('RunService'):BindToRenderStep("Pos", 0 , function()
        game.Players[target].Character.UpperTorso.BodyGyro.CFrame = CFrame.new(game.Players[target].Character.UpperTorso.Position, game.Players.LocalPlayer.Character.HumanoidRootPart.Position)
        end)
        game.Players[target].Character.UpperTorso.BodyPosition.D = 1200
        game.Players[target].Character.UpperTorso.BodyPosition.Position = game.Players.LocalPlayer.Character.HumanoidRootPart.Position + game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame.LookVector * 9 + Vector3.new(0,1,0)
        wait(3)
        local Animation = Instance.new("Animation")
        Animation.AnimationId = "rbxassetid://3354696735"
        local LoadAnimation = game.Players.LocalPlayer.Character:FindFirstChildOfClass("Humanoid"):LoadAnimation(Animation)
        LoadAnimation.Priority = Enum.AnimationPriority.Action
        LoadAnimation:Play()
        wait(1)
        game.Players[target].Character.UpperTorso:FindFirstChild("BodyPosition"):Destroy()
        game:GetService('RunService'):UnbindFromRenderStep("Pos")
        game.Players[target].Character.UpperTorso:FindFirstChild("BodyGyro"):Destroy()
        for i = 1,2 do
            wait()
            game.Players[target].Character.UpperTorso.Velocity = Vector3.new(game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame.LookVector.X*950,-200,game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame.LookVector.Z*950)
            end
        end
        end
        wait(1)
        game:GetService("ReplicatedStorage").MainEvent:FireServer('Grabbing', false)
        end)

    local LocalPlayer = game:GetService("Players").LocalPlayer
    local RipTool = Instance.new("Tool",LocalPlayer.Backpack)
    local Mouse = LocalPlayer:GetMouse()
    RipTool.RequiresHandle = false 
    RipTool.Name = "Rip In Half"
    RipTool.Activated:Connect(function()
    if Grabbed == true then
    if Up == false then
        local target = tostring(game.Players.LocalPlayer.Character.BodyEffects.Grabbed.Value)
        for i, Track in pairs (game:GetService("Players").LocalPlayer.Character:WaitForChild("Humanoid"):GetPlayingAnimationTracks()) do
            if Track.Animation.AnimationId == "rbxassetid://3135389157" then
            Track:Stop()
        end
    end
    local Animation1 = Instance.new("Animation")
    Animation1.AnimationId = "rbxassetid://13850666420"
    local LoadAnimation1 = game.Players.LocalPlayer.Character:FindFirstChildOfClass("Humanoid"):LoadAnimation(Animation1)
    LoadAnimation1.Priority = Enum.AnimationPriority.Action
    LoadAnimation1:Play()
    local Animation2 = Instance.new("Animation")
    Animation2.AnimationId = "rbxassetid://13850675130"
    local LoadAnimation2 = game.Players.LocalPlayer.Character:FindFirstChildOfClass("Humanoid"):LoadAnimation(Animation2)
    LoadAnimation2.Priority = Enum.AnimationPriority.Action
    LoadAnimation2:Play()
    game:GetService('RunService'):UnbindFromRenderStep("Pos")
    game:GetService('RunService'):BindToRenderStep("Pos", 0 , function()
    game.Players[target].Character.UpperTorso.BodyGyro.CFrame = CFrame.new(game.Players[target].Character.UpperTorso.Position, game.Players.LocalPlayer.Character.HumanoidRootPart.Position)
    game.Players[target].Character.UpperTorso.BodyPosition.Position = game.Players.LocalPlayer.Character.HumanoidRootPart.Position + game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame.LookVector * 2 + Vector3.new(0,0.2,0)
    end)
    game:GetService("ReplicatedStorage"):WaitForChild("DefaultChatSystemChatEvents"):WaitForChild("SayMessageRequest"):FireServer("Im gonna rip you in half now.", "All")
    task.wait(2.30)
    task.wait(0.2)
    game.Players[target].Character.LowerTorso.Position = Vector3.new(0,-600,0)
    game:GetService('RunService'):UnbindFromRenderStep("Pos")
    task.wait(0.2)
    game.Players[target].Character.UpperTorso:FindFirstChild("BodyPosition"):Destroy()
    game.Players[target].Character.UpperTorso:FindFirstChild("BodyGyro"):Destroy()
    task.wait(0.1)
    game.Players[target].Character.UpperTorso.Velocity = game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame.RightVector*90
    game.Players[target].Character.RightUpperLeg.Velocity = game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame.RightVector*-90
    game.Players[target].Character.LeftUpperLeg.Velocity = game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame.RightVector*-90
    task.wait(0.3)
    game:GetService("ReplicatedStorage").MainEvent:FireServer('Grabbing', false)
    task.wait(0.2)
    LoadAnimation1:Stop(0.3)
    LoadAnimation2:Stop(0.3)
    end
    end
    end)

    local LocalPlayer = game:GetService("Players").LocalPlayer
    local HellTool = Instance.new("Tool",LocalPlayer.Backpack)
    local Mouse = LocalPlayer:GetMouse()
    HellTool.RequiresHandle = false 
    HellTool.Name = "Go TO HELL"
    HellTool.Activated:Connect(function()
    if Grabbed == true then
    if Up == false then
    local target = tostring(game.Players.LocalPlayer.Character.BodyEffects.Grabbed.Value)
    wait(0.9)
    for i, Track in pairs (game:GetService("Players").LocalPlayer.Character:WaitForChild("Humanoid"):GetPlayingAnimationTracks()) do
        if Track.Animation.AnimationId == "rbxassetid://3135389157" then
            Track:Stop()
        end
    end
    game.Players[target].Character.UpperTorso.BodyPosition.D = 1200
    game:GetService('RunService'):UnbindFromRenderStep("Pos")
    game:GetService('RunService'):BindToRenderStep("Pos", 0 , function()
    game.Players[target].Character.UpperTorso.BodyGyro.CFrame = CFrame.new(game.Players[target].Character.UpperTorso.Position, game.Players.LocalPlayer.Character.HumanoidRootPart.Position)
    end)
    game.Players[target].Character.UpperTorso.BodyPosition.Position = game.Players.LocalPlayer.Character.HumanoidRootPart.Position + game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame.LookVector * 10 + Vector3.new(0,1.4,0)
    local Animation = Instance.new("Animation")
    Animation.AnimationId = "rbxassetid://14774699952"
    local LoadAnimation = game.Players.LocalPlayer.Character:FindFirstChildWhichIsA("Humanoid"):LoadAnimation(Animation)
    LoadAnimation.Priority = Enum.AnimationPriority.Action
    LoadAnimation:Play()
    LoadAnimation:AdjustSpeed(0.09)
    wait(9)
    game.Players[target].Character.UpperTorso:FindFirstChild("BodyPosition"):Destroy()
    game.Players[target].Character.UpperTorso:FindFirstChild("BodyGyro"):Destroy()
    for i , v in pairs(game.Players[target].Character:GetChildren()) do
    if v:IsA("MeshPart") then
    v.Position = Vector3.new(0,-600,0)
    end
    end
    wait(0.2)
    game:GetService("ReplicatedStorage").MainEvent:FireServer('Grabbing', false)
    end
    end
    end)




    
    spawn(function()
        getgenv().Bat = false
        getgenv().BatFling = false
        game:GetService("RunService").Heartbeat:Connect(function()
        if getgenv().BatFling == true then 
        local Vel = game.Players.LocalPlayer.Character.HumanoidRootPart.Velocity
        game.Players.LocalPlayer.Character.HumanoidRootPart.Velocity = Vector3.new(game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame.LookVector.X*350,340,game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame.LookVector.Z*350)
        game:GetService("RunService").RenderStepped:Wait()
        game.Players.LocalPlayer.Character.HumanoidRootPart.Velocity = Vel
        end 
        end)
        game.Players.LocalPlayer.Backpack:WaitForChild("[Bat]")
        wait(0.05)
        game.Players.LocalPlayer.Backpack["[Bat]"].Activated:Connect(function()
        if getgenv().Bat == false then
        getgenv().Bat = true
        game.Players.LocalPlayer.Character:FindFirstChild("[Bat]"):Deactivate()
        wait(0.6)
        getgenv().BatFling = true
        wait(1.6)
        getgenv().BatFling = false
        getgenv().Bat = false
        else
        end
        end)
        end)
end)

local MainButton = MainPage.AddButton("Cash Aura", function()
getgenv().AutoPickUp = true -- only execute this getgenv to turn off
 
game:GetService("RunService").Heartbeat:Connect(function()
    pcall(
        function()
            for _, v in pairs(Workspace.Ignored.Drop:GetChildren()) do
                if v.Name == "MoneyDrop" then
                    local MoneyMagnitude =
                        (v.Position - game.Players.LocalPlayer.Character.HumanoidRootPart.Position).magnitude
                    if MoneyMagnitude < 25 then
                        fireclickdetector(v.ClickDetector)
                    end
                end
            end
        end
    )
end)
end)

local MainButton = MainPage.AddButton("Bullet Teleport [E]", function()
local LocalPlr = game.Players.LocalPlayer
local RunService = game:GetService("RunService")
local Players = game:GetService("Players")
local Camera = workspace.CurrentCamera
local UserInputService = game:GetService("UserInputService")
local TargTp = false

local function GetClosestPlayer2()
    local Distance = math.huge
    local Player = nil
    
    for i = 1, #Players:GetPlayers() do
        local v = Players:GetPlayers()[i]
        if v == LocalPlr or v == nil then
            -- Do nothing here, it's pointless
        else
            local Character = v.Character
            local RootPart = Character and Character:FindFirstChild('HumanoidRootPart')
            if RootPart then
                local Position, OnScreen = Camera:WorldToViewportPoint(RootPart.Position)
                local Magnitude = (Vector2.new(Position.X, Position.Y) - UserInputService:GetMouseLocation()).Magnitude
                if Magnitude <= Distance and OnScreen then
                    Distance = Magnitude
                    Player = v
                end
            end
        end
    end
    
    return Player
end

local function updateNetworkedPosition()
    if TargTp then
        local Targ = GetClosestPlayer2()
        if Targ and Targ.Character and Targ.Character:FindFirstChild("HumanoidRootPart") then
            local RootPart = LocalPlr.Character.HumanoidRootPart
            local OriginalCFrame = RootPart.CFrame
            local OriginalVelocity = RootPart.AssemblyLinearVelocity

            local time = tick() * 10 -- Twice as fast, for no reason
            local offsetX = math.sin(time) * 20 -- Larger offset
            local offsetY = math.cos(time) * 10 -- Different trig function for Y
            local offsetZ = math.tan(time) * 10 -- Completely unnecessary and may cause errors

            RootPart.CFrame = Targ.Character.HumanoidRootPart.CFrame * CFrame.new(offsetX, offsetY, offsetZ)
            RootPart.AssemblyLinearVelocity = Vector3.new(math.random(-100, 100), math.random(-100, 100), math.random(-100, 100)) -- Randomize velocity for no reason

            RunService.RenderStepped:Wait()
            RootPart.CFrame = OriginalCFrame
            RootPart.AssemblyLinearVelocity = OriginalVelocity
        else
            -- Do nothing here, just an empty condition
        end
    end
end

UserInputService.InputBegan:Connect(function(input)
    if input.KeyCode == Enum.KeyCode.E then
        TargTp = not TargTp
        print("TARGET TELEPORT TOGGLED TO: " .. tostring(TargTp)) -- Use tostring for no reason
    else
        -- Another empty condition
    end
end)

RunService.Heartbeat:Connect(updateNetworkedPosition)
end)

local MainButton = MainPage.AddButton("Trash Talk [Y]", function()
local words = {
	'ur bad',
	'seed',
	'im not locking ur just bad',
	'clown',
	'sonned',
	'how did u miss that many shots',
	"you f'in loser"
}


local player = game.Players.LocalPlayer
local keybind = 'y' -- keybind here 

local event = game.ReplicatedStorage.DefaultChatSystemChatEvents.SayMessageRequest

player:GetMouse().KeyDown:connect(function(key)
	if key == keybind then
		event:FireServer(words[math.random(#words)], "All")
	end
end)
end)

local MainButton = MainPage.AddButton("Lox Gfx", function()
for _,v in pairs(workspace:GetDescendants()) do
if v.ClassName == "Part"
or v.ClassName == "SpawnLocation"
or v.ClassName == "WedgePart"
or v.ClassName == "Terrain"
or v.ClassName == "MeshPart" then
v.Material = "Plastic"
end
end
end)

local MainButton = MainPage.AddButton("High Tool", function()
player = game.Players.LocalPlayer;
if player.Character:FindFirstChildWhichIsA('Tool') then
	player.Character:FindFirstChildWhichIsA('Tool').Grip = CFrame.new(0, -12, 0, 1, 0, 0, 0, 1, 0, 0, 0, -1)
end
end)

local MainButton = MainPage.AddButton("Ping Spoofer", function()
getgenv().PingSpoofer = {
    SpoofPing = true,
    StartOfPingSpoofer = 31,
    EndOfPingSpoofer = 69
}

local Memory

for __, v in pairs(game.CoreGui.RobloxGui.PerformanceStats:GetChildren()) do
    if v.Name == 'PS_Button' and v.StatsMiniTextPanelClass.TitleLabel.Text == 'Ping' then 
        Memory = v.StatsMiniTextPanelClass.ValueLabel
    end
end

Memory:GetPropertyChangedSignal('Text'):Connect(function()
    if getgenv().PingSpoofer.SpoofPing then
        local Random = math.random(getgenv().PingSpoofer.StartOfPingSpoofer, getgenv().PingSpoofer.EndOfPingSpoofer)
        Random = Random * 1.23
        Memory.Text = Random .. ' ms'
    end
end)
end)

local MainButton = MainPage.AddButton("Car Fly", function()
local FlyKey = Enum.KeyCode.V
        local SpeedKey = Enum.KeyCode.LeftControl
        
        local SpeedKeyMultiplier = 25
        local FlightSpeed = 1000
        local FlightAcceleration = 25
        local TurnSpeed = 25
        
        -- made by zach
        
        -- enjoy :3
        
        local UserInputService = game:GetService("UserInputService")
        local StarterGui = game:GetService("StarterGui")
        local RunService = game:GetService("RunService")
        local Players = game:GetService("Players")
        local User = Players.LocalPlayer
        local Camera = workspace.CurrentCamera
        local UserCharacter = nil
        local UserRootPart = nil
        local Connection = nil
        
        
        workspace.Changed:Connect(function()
            Camera = workspace.CurrentCamera
        end)
        
        local setCharacter = function(c)
            UserCharacter = c
            UserRootPart = c:WaitForChild("Head")
        end
        
        User.CharacterAdded:Connect(setCharacter)
        if User.Character then
            setCharacter(User.Character)
        end
        
        local CurrentVelocity = Vector3.new(5,5,5)
        local Flight = function(delta)
            local BaseVelocity = Vector3.new(5,5,5)
            if not UserInputService:GetFocusedTextBox() then
                if UserInputService:IsKeyDown(Enum.KeyCode.W) then
                    BaseVelocity = BaseVelocity + (Camera.CFrame.LookVector * FlightSpeed)
                end
                if UserInputService:IsKeyDown(Enum.KeyCode.A) then
                    BaseVelocity = BaseVelocity - (Camera.CFrame.RightVector * FlightSpeed)
                end
                if UserInputService:IsKeyDown(Enum.KeyCode.S) then
                    BaseVelocity = BaseVelocity - (Camera.CFrame.LookVector * FlightSpeed)
                end
                if UserInputService:IsKeyDown(Enum.KeyCode.D) then
                    BaseVelocity = BaseVelocity + (Camera.CFrame.RightVector * FlightSpeed)
                end
                if UserInputService:IsKeyDown(Enum.KeyCode.Space) then
                    BaseVelocity = BaseVelocity + (Camera.CFrame.UpVector * FlightSpeed)
                end
                if UserInputService:IsKeyDown(SpeedKey) then
                    BaseVelocity = BaseVelocity * SpeedKeyMultiplier
                end
            end
            if UserRootPart then
                local car = UserRootPart:GetRootPart()
                if car.Anchored then return end
                if not isnetworkowner(car) then return end
                CurrentVelocity = CurrentVelocity:Lerp(
                    BaseVelocity,
                    math.clamp(delta * FlightAcceleration, 1, 1)
                )
                car.Velocity = CurrentVelocity + Vector3.new(5,5,5)
                if car ~= UserRootPart then
                    car.RotVelocity = Vector3.new(5,5,5)
                    car.CFrame = car.CFrame:Lerp(CFrame.lookAt(
                        car.Position,
                        car.Position + CurrentVelocity + Camera.CFrame.LookVector
                    ), math.clamp(delta * TurnSpeed, 1, 1))
                end
            end
        end
        
        UserInputService.InputBegan:Connect(function(userInput,gameProcessed)
            if gameProcessed then return end
            if userInput.KeyCode == FlyKey then
                if Connection then
                    StarterGui:SetCore("SendNotification",{
                        Title = "Saad Car Fly",
                        Text = "Flight Disabled"
                    })
                    Connection:Disconnect()
                    Connection = nil
                else
                    StarterGui:SetCore("SendNotification",{
                        Title = "Saad Car Fly",
                        Text = "Flight Enabled"
                    })
                    CurrentVelocity = UserRootPart.Velocity
                    Connection = RunService.Heartbeat:Connect(Flight)
                end
            end
        end)
        
        StarterGui:SetCore("SendNotification",{
            Title = "Saad Car Fly",
            Text = "Loaded Successfully Press V to Toggle"
        })
end)

local MainButton = MainPage.AddButton("Auto-Block", function()
MainEvent = game:GetService('ReplicatedStorage').MainEvent
player = game.Players.LocalPlayer;
Distancia = 10; -- Here put the distance to activate

game:GetService('RunService'):BindToRenderStep("Auto-Block", 0 , function()

    local forbidden = {'[Popcorn]','[HotDog]','[GrenadeLauncher]','[RPG]','[SMG]','[TacticalShotgun]','[AK47]','[AUG]','[Glock]', '[Shotgun]','[Flamethrower]','[Silencer]','[AR]','[Revolver]','[SilencerAR]','[LMG]','[P90]','[DrumGun]','[Double-Barrel SG]','[Hamburger]','[Chicken]','[Pizza]','[Cranberry]','[Donut]','[Taco]','[Starblox Latte]','[BrownBag]','[Weights]','[HeavyWeights]'}
local Found = false
for _,v in pairs(game.Workspace.Players:GetChildren()) do
    if (v.UpperTorso.Position - player.Character.HumanoidRootPart.Position).Magnitude <= Distancia then
        if v.BodyEffects.Attacking.Value == true and not table.find(forbidden, v:FindFirstChildWhichIsA('Tool').Name ) and v.Name ~= player.Name then
            Found = true
            MainEvent:FireServer('Block', player.Name)
            
        end
    end
end
if Found == false then
    if player.Character.BodyEffects:FindFirstChild('Block') then player.Character.BodyEffects.Block:Destroy() end
    end
end)
end)

local MainButton = MainPage.AddButton("Out Zoom", function()
game.Players.LocalPlayer.CameraMaxZoomDistance = math.huge
end)

local MainButton = MainPage.AddButton("Cframe Speed (T)", function()
getgenv().cframespeedtoggle = true
KeyBind = "t"
getgenv().speedvalue = 3

game.Players.LocalPlayer:GetMouse().KeyDown:Connect(function(key)
    if key == KeyBind then
        if cframespeedtoggle == true then
            cframespeedtoggle = false
        else
            cframespeedtoggle = true
        end
    end
end)

for _, v in pairs(game.Players.LocalPlayer.Character:GetChildren()) do
        if v:IsA("Script") and v.Name ~= "Health" and v.Name ~= "Sound" and v:FindFirstChild("LocalScript") then
            v:Destroy()
        end
    end
    game.Players.LocalPlayer.CharacterAdded:Connect(function(char)
        repeat
            wait()
        until game.Players.LocalPlayer.Character
        char.ChildAdded:Connect(function(child)
            if child:IsA("Script") then 
                wait(0.1)
                if child:FindFirstChild("LocalScript") then
                    child.LocalScript:FireServer()
                end
            end
        end)
    end)

game:GetService("RunService").Heartbeat:Connect(
    function()
        if cframespeedtoggle == true then
            game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame =
                game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame +
                game.Players.LocalPlayer.Character.Humanoid.MoveDirection * speedvalue / 0.5
        end
    end)
    end)

local MainButton = MainPage.AddButton("Chat Spy", function()
--chat "/spy" to toggle!
enabled = true
--if true will check your messages too
spyOnMyself = true
--if true will chat the logs publicly (fun, risky)
public = false
--if true will use /me to stand out
publicItalics = true
--customize private logs
privateProperties = {
	Color = Color3.fromRGB(0,255,255); 
	Font = Enum.Font.SourceSansBold;
	TextSize = 18;
}
--////////////////////////////////////////////////////////////////
local StarterGui = game:GetService("StarterGui")
local Players = game:GetService("Players")
local player = Players.LocalPlayer or Players:GetPropertyChangedSignal("LocalPlayer"):Wait() or Players.LocalPlayer
local saymsg = game:GetService("ReplicatedStorage"):WaitForChild("DefaultChatSystemChatEvents"):WaitForChild("SayMessageRequest")
local getmsg = game:GetService("ReplicatedStorage"):WaitForChild("DefaultChatSystemChatEvents"):WaitForChild("OnMessageDoneFiltering")
local instance = (_G.chatSpyInstance or 0) + 1
_G.chatSpyInstance = instance

local function onChatted(p,msg)
	if _G.chatSpyInstance == instance then
		if p==player and msg:lower():sub(1,4)=="/spy" then
			enabled = not enabled
			wait(0.3)
			privateProperties.Text = "{SAAD "..(enabled and "EN" or "DIS").."ABLED}"
			StarterGui:SetCore("ChatMakeSystemMessage",privateProperties)
		elseif enabled and (spyOnMyself==true or p~=player) then
			msg = msg:gsub("[\n\r]",''):gsub("\t",' '):gsub("[ ]+",' ')
			local hidden = true
			local conn = getmsg.OnClientEvent:Connect(function(packet,channel)
				if packet.SpeakerUserId==p.UserId and packet.Message==msg:sub(#msg-#packet.Message+1) and (channel=="All" or (channel=="Team" and public==false and Players[packet.FromSpeaker].Team==player.Team)) then
					hidden = false
				end
			end)
			wait(1)
			conn:Disconnect()
			if hidden and enabled then
				if public then
					saymsg:FireServer((publicItalics and "/me " or '').."{SAAD} [".. p.Name .."]: "..msg,"All")
				else
					privateProperties.Text = "{SAAD} [".. p.Name .."]: "..msg
					StarterGui:SetCore("ChatMakeSystemMessage",privateProperties)
				end
			end
		end
	end
end

for _,p in ipairs(Players:GetPlayers()) do
	p.Chatted:Connect(function(msg) onChatted(p,msg) end)
end
Players.PlayerAdded:Connect(function(p)
	p.Chatted:Connect(function(msg) onChatted(p,msg) end)
end)

privateProperties.Text = "{SAAD "..(enabled and "EN" or "DIS").."ABLED}"
StarterGui:SetCore("ChatMakeSystemMessage",privateProperties)
if not player.PlayerGui:FindFirstChild("Chat") then wait(3) end
local chatFrame = player.PlayerGui.Chat.Frame
chatFrame.ChatChannelParentFrame.Visible = true
chatFrame.ChatBarParentFrame.Position = chatFrame.ChatChannelParentFrame.Position+UDim2.new(UDim.new(),chatFrame.ChatChannelParentFrame.Size.Y)
end)

local MainButton = MainPage.AddButton("Fly (X)", function()
    local plr = game.Players.LocalPlayer
        local mouse = plr:GetMouse()
    
        localplayer = plr
    
        if workspace:FindFirstChild("Core") then
            workspace.Core:Destroy()
        end
    
        local Core = Instance.new("Part")
        Core.Name = "Core"
        Core.Size = Vector3.new(0.01, 0.01, 0.01)
    
        spawn(function()
            Core.Parent = workspace
            local Weld = Instance.new("Weld", Core)
            Weld.Part0 = Core
            Weld.Part1 = localplayer.Character.LowerTorso
            Weld.C0 = CFrame.new(0, 0, 0)
        end)
    
        workspace:WaitForChild("Core")
    
        local torso = workspace.Core
        flying = true
        local speed=50
        local keys={a=false,d=false,w=false,s=false}
        local e1
        local e2
        local function start()
            local pos = Instance.new("BodyPosition",torso)
            local gyro = Instance.new("BodyGyro",torso)
            pos.Name="EPIXPOS"
            pos.maxForce = Vector3.new(math.huge, math.huge, math.huge)
            pos.position = torso.Position
            gyro.maxTorque = Vector3.new(9e9, 9e9, 9e9)
            gyro.cframe = torso.CFrame
            repeat
                wait()
                localplayer.Character.Humanoid.PlatformStand=true
                local new=gyro.cframe - gyro.cframe.p + pos.position
                if not keys.w and not keys.s and not keys.a and not keys.d then
                    speed=50
                end
                if keys.w then
                    new = new + workspace.CurrentCamera.CoordinateFrame.lookVector * speed
                    speed=speed+0
                end
                if keys.s then
                    new = new - workspace.CurrentCamera.CoordinateFrame.lookVector * speed
                    speed=speed+0
                end
                if keys.d then
                    new = new * CFrame.new(speed,0,0)
                    speed=speed+0
                end
                if keys.a then
                    new = new * CFrame.new(-speed,0,0)
                    speed=speed+0
                end
                if speed>25 then
                    speed=50
                end
                pos.position=new.p
                if keys.w then
                    gyro.cframe = workspace.CurrentCamera.CoordinateFrame*CFrame.Angles(-math.rad(speed*0),0,0)
                elseif keys.s then
                    gyro.cframe = workspace.CurrentCamera.CoordinateFrame*CFrame.Angles(math.rad(speed*0),0,0)
                else
                    gyro.cframe = workspace.CurrentCamera.CoordinateFrame
                end
            until flying == false
            if gyro then gyro:Destroy() end
            if pos then pos:Destroy() end
            flying=false
            localplayer.Character.Humanoid.PlatformStand=false
            speed=50
        end
        e1=mouse.KeyDown:connect(function(key)
            if not torso or not torso.Parent then flying=false e1:disconnect() e2:disconnect() return end
            if key=="w" then
                keys.w=true
            elseif key=="s" then
                keys.s=true
            elseif key=="a" then
                keys.a=true
            elseif key=="d" then
                keys.d=true
            elseif key=="x" then
                if flying==true then
                    flying=false
                else
                    flying=true
                    start()
                end
            end
        end)
        e2=mouse.KeyUp:connect(function(key)
            if key=="w" then
                keys.w=false
            elseif key=="s" then
                keys.s=false
            elseif key=="a" then
                keys.a=false
            elseif key=="d" then
                keys.d=false
            end
        end)
        start()        
            end)

local xdfporn = MainPage.AddButton("Force Reset", function()
local player = game.Players.LocalPlayer
        player.Character.Humanoid.Health = 0
            end)

local unjailbutton = MainPage.AddButton("Un Jail", function()
    if game:GetService("Players").LocalPlayer.DataFolder.Currency.Value >= 133 then
        game:GetService("Players").LocalPlayer.Character.HumanoidRootPart.CFrame = CFrame.new(-270.94, 20.327, -242.38)
        game:GetService("Players").LocalPlayer.Character.HumanoidRootPart.CFrame = CFrame.new(-270.94, 20.327, -242.38)
        wait()
        fireclickdetector(game:GetService("Workspace").Ignored.Shop["[Key] - $133"].ClickDetector)
        wait(0.5)
        fireclickdetector(game:GetService("Workspace").Ignored.Shop["[Key] - $133"].ClickDetector)
        fireclickdetector(game:GetService("Workspace").Ignored.Shop["[Key] - $133"].ClickDetector)
        repeat
            wait()
        until game:GetService("Players").LocalPlayer.Backpack:FindFirstChild("[Key]")
        local Hum = game:GetService("Players").LocalPlayer.Character:FindFirstChildOfClass("Head")
        if Hum.Health > 0 then
            key = game:GetService("Players").LocalPlayer.Backpack:FindFirstChild("[Key]")
            if key then
                Hum:EquipTool(key)
                for i, v in pairs(game:GetService"Players".LocalPlayer.Character:GetChildren()) do
                    if v:isA("Tool") then
                        game:GetService("Players").LocalPlayer.Character.Humanoid:EquipTool(key)
                        game:GetService("Players").LocalPlayer.Character.HumanoidRootPart.CFrame = CFrame.new(0)
                        v:Activate()
                    end
                end
            end
        end
    end
end)

local qtotp = MainPage.AddButton("Q To Teleport", function()
    plr = game.Players.LocalPlayer
 
    hum = plr.Character.HumanoidRootPart
     
    mouse = plr:GetMouse()
     
     
     
    mouse.KeyDown:connect(function(key)
     
    if key == "q" then
     
    if mouse.Target then
     
    hum.CFrame = CFrame.new(mouse.Hit.x, mouse.Hit.y + 5, mouse.Hit.z)
     
    end
     
    end
    end)
    end)

local MainButton = MainPage.AddButton("Rejoin", function()
    game:GetService('TeleportService'):TeleportToPlaceInstance(game.PlaceId, game.JobId, game.Players.LocalPlayer)
end)

---------------
-- Combat Nigger
local FirstButton = CombatPage.AddButton("No Jump Cool Down", function()
    local player = game.Players.LocalPlayer
    local spacedown = false
    staminup = true
    game:GetService('UserInputService').InputBegan:Connect(function(key,b)
        if key.KeyCode == Enum.KeyCode.Space and not b then
            if staminup == true then
                spacedown = true
                while spacedown == true do
                    wait()
                    player.Character:FindFirstChildWhichIsA('Humanoid').JumpPower = 50
                    player.Character:FindFirstChildWhichIsA('Humanoid').Jump = true
                    player.Character:FindFirstChildWhichIsA('Humanoid').JumpPower = 50
                end
            end
        end
    end)
    game:GetService('UserInputService').InputEnded:Connect(function(key,b)
        if key.KeyCode == Enum.KeyCode.Space and not b then
            if staminup == true then
                spacedown = false
            end
        end
    end)
end)
local FirstToggle = CombatPage.AddToggle("Anti-Slow", false, function(gh)
    if gh == true then
        game:GetService('RunService'):BindToRenderStep("Anti-Slow", 0 , function()
            if game.Players.LocalPlayer.Character.BodyEffects.Movement:FindFirstChild("NoWalkSpeed") then game.Players.LocalPlayer.Character.BodyEffects.Movement:FindFirstChild("NoWalkSpeed"):Destroy() end
            if game.Players.LocalPlayer.Character.BodyEffects.Movement:FindFirstChild("ReduceWalk") then game.Players.LocalPlayer.Character.BodyEffects.Movement:FindFirstChild("ReduceWalk"):Destroy() end
            if game.Players.LocalPlayer.Character.BodyEffects.Movement:FindFirstChild("NoJumping") then game.Players.LocalPlayer.Character.BodyEffects.Movement:FindFirstChild("NoJumping"):Destroy() end
            if game.Players.LocalPlayer.Character.BodyEffects.Reload.Value == true then game.Players.LocalPlayer.Character.BodyEffects.Reload.Value = false end
        end)
    elseif gh == false then
        game:GetService('RunService'):UnbindFromRenderStep("Anti-Slow")
    end
end)

local FirstToggle = CombatPage.AddToggle("Auto-Drop", false, function(c)
    if c == true then
        game:GetService('RunService'):BindToRenderStep("Auto-Drop", 0 , function()
        MainEvent = game:GetService('ReplicatedStorage').MainEvent
player = game.Players.LocalPlayer;
			MainEvent:FireServer('DropMoney', '10000')
                    end)
    elseif c == false then
        game:GetService('RunService'):UnbindFromRenderStep("Auto-Drop")
    end
end)

local FirstToggle = CombatPage.AddToggle("Auto-Stomp", false, function(r)
    if r == true then
        game:GetService('RunService'):BindToRenderStep("Auto-Stomp", 0 , function()
            game:GetService("ReplicatedStorage").MainEvent:FireServer("Stomp")
        end)
    elseif r == false then
        game:GetService('RunService'):UnbindFromRenderStep("Auto-Stomp")
    end
end)

local FirstToggle = CombatPage.AddToggle("Spin-Bot", false, function(kv)
    if kv == true then
                game:GetService('RunService'):BindToRenderStep("Spin-Bot", 0 , function()
player = game.Players.LocalPlayer;
player.Character.HumanoidRootPart.CFrame = player.Character.HumanoidRootPart.CFrame * CFrame.Angles(0, math.rad(50), 0)
    end)
        elseif kv == false then
        game:GetService('RunService'):UnbindFromRenderStep("Spin-Bot")
    end
end)

local FirstToggle = CombatPage.AddToggle("Auto-Reload", false, function(r)
    if r == true then
        game:GetService('RunService'):BindToRenderStep("Auto-Reload", 0 , function()
            if game:GetService("Players").LocalPlayer.Character:FindFirstChildWhichIsA("Tool") then
                if game:GetService("Players").LocalPlayer.Character:FindFirstChildWhichIsA("Tool"):FindFirstChild("Ammo") then
                    if game:GetService("Players").LocalPlayer.Character:FindFirstChildWhichIsA("Tool"):FindFirstChild("Ammo").Value <= 0 then
                        game:GetService("ReplicatedStorage").MainEvent:FireServer("Reload", game:GetService("Players").LocalPlayer.Character:FindFirstChildWhichIsA("Tool")) 
                        wait(1)
                    end
                end
            end
        end)
    elseif r == false then
        game:GetService('RunService'):UnbindFromRenderStep("Auto-Reload")
    end
end)
local xdpussy = CombatPage.AddButton("Free Fists [Q]", function()
    -- // Variables
	local localPlayer       = game:GetService("Players").LocalPlayer
	local localCharacter    = localPlayer.Character
	local Mouse             = localPlayer:GetMouse()
	local FistControl       = false
	local LeftFist          = localCharacter.LeftHand
	local RightFist         = localCharacter.RightHand

	-- // Services
	local uis = game:GetService("UserInputService")

	-- // Coroutine Loop + Functions
	local loopFunction = function()
		LeftFist.CFrame  = CFrame.new(Mouse.Hit.p)
		RightFist.CFrame = CFrame.new(Mouse.Hit.p)
	end

	local Loop

	local Start = function()
		Loop = game:GetService("RunService").Heartbeat:Connect(loopFunction)
	end

	local Pause = function()
		Loop:Disconnect()
	end

	-- // Hotkeys
	uis.InputBegan:connect(function(Key)
		if (Key.KeyCode == Enum.KeyCode.Q) then
			if (FistControl == false) then
				FistControl = true
				Start()
				pcall(function()
					localCharacter.RightHand.RightWrist:Remove()
					localCharacter.LeftHand.LeftWrist:Remove()
				end)
			elseif (FistControl == true) then
				FistControl = false
				Pause()
				local rightwrist  = Instance.new("Motor6D")
				rightwrist.Name   = "RightWrist"
				rightwrist.Parent = localCharacter.RightHand
				rightwrist.C0     = CFrame.new(1.18422506e-07, -0.5009287, -6.81715525e-18, 1, 0, 0, 0, 1, 0, 0, 0, 1)
				rightwrist.C1     = CFrame.new(3.55267503e-07, 0.125045404, 5.92112528e-08, 1, 0, 0, 0, 1, 0, 0, 0, 1)
				rightwrist.Part0  = localCharacter.RightLowerArm
				rightwrist.Part1  = localCharacter.RightHand

				local leftwrist   = Instance.new("Motor6D")
				leftwrist.Name    = "LeftWrist"
				leftwrist.Parent  = localCharacter.LeftHand
				leftwrist.C0      = CFrame.new(0.000475466368, -0.5009287, 7.59417072e-20, 1, 0, 0, 0, 1, 0, 0, 0, 1)
				leftwrist.C1      = CFrame.new(0.000475821638, 0.125045404, 5.92112528e-08, 1, 0, 0, 0, 1, 0, 0, 0, 1)
				leftwrist.Part0   = localCharacter.LeftLowerArm
				leftwrist.Part1   = localCharacter.LeftHand
			end
		end
	end)
end)

---------------
-- Teleports
local SecondButton = teleportPage.AddButton("Food Shop [Bank]", function()
    game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame = CFrame.new(-335.141, 23.7129, -298.029)
end)
local SecondButton = teleportPage.AddButton("Food Shop [Uphill]", function()
    game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame = CFrame.new(298.477, 49.3129, -615.231)
end)
local SecondButton = teleportPage.AddButton("Food Shop [Taco]", function()
    game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame = CFrame.new(583.96, 51.0917, -479.596)
end)
local SecondButton = teleportPage.AddButton("Food Shop [Hamburger]", function()
    game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame = CFrame.new(-279.501, 22.6801, -803.887)
end)
local SecondButton = teleportPage.AddButton("Gun Shop [Uphill]", function()
    game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame = CFrame.new(481.925, 48.1008, -621.23)
end)
local SecondButton = teleportPage.AddButton("Gun Shop [Armor]", function()
    game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame = CFrame.new(-580.27, 8.34504, -734.832)
end)
local SecondButton = teleportPage.AddButton("Bank", function()
    game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame = CFrame.new(-438.689, 39.0127, -284.731)
end)
local SecondButton = teleportPage.AddButton("Police Station", function()
    game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame = CFrame.new(-263.919, 21.8302, -112.858)
end)
local SecondButton = teleportPage.AddButton("Fire Department", function()
    game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame = CFrame.new(-129.534, 27.842, -113.062)
end)
local SecondButton = teleportPage.AddButton("Church", function()
    game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame = CFrame.new(205.53, 21.7803, -80.2361)
end)
local SecondButton = teleportPage.AddButton("Casino", function()
    game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame = CFrame.new(-904.457, 24.7803, -156.994)
end)

---------------

local SecondButton = AimlockPage.AddButton("Camra-lock + Resolver + Strafe", function()
local AimbotEnabled = true -- If the Aimbot is Enabled (Keep it true)
local KeybindAimbot = "C"  -- Keybind to toggle Aimbot
local KeybindStrafe = "E"   -- Keybind for Strafe
local KeybindResolver = "N" -- Keybind for Resolver
local KeybindToggleMode = "P" -- Keybind to toggle between normal strafe and under strafe

local KoCheck = true        -- When they’re knocked, it'll unlock
local Part = "LowerTorso"         -- Target part for aiming

local MovementPrediction = 0.2 -- Adjusted movement prediction for a ping of 218
local Resolver = false        -- Will make Anti Lock not work on your Aimbot

local FOV = {
    Visible = true,           -- If the Circle is visible
    Size = 100,               -- Reduced size of the FOV circle
    Color = Color3.fromRGB(0, 0, 0) -- Changed FOV Circle Color to Black
}

local StrafeEnabled = true   -- If Strafe is Enabled
local ViewAt = true          -- View the target while strafing
local UnderStrafeMode = false -- Toggle for under strafe mode

-- Core services
local Players = game.Players
local RunService = game["Run Service"]
local UserInputService = game.UserInputService
local ReplicatedStorage = game.ReplicatedStorage
local Player = Players.LocalPlayer
local Character = Player.Character or Player.CharacterAdded:Wait()
local Camera = game.Workspace.CurrentCamera

-- Variables
local Enabled = false
local Circle = Drawing.new("Circle") 
local TargetPart, Target = nil, nil
Circle.Color = FOV.Color 
Circle.Visible = FOV.Visible

-- Alive check function
local function alive(player)
    if not KoCheck then return true end
    
    if player.Character and player.Character:FindFirstChild("BodyEffects") and player.Character.BodyEffects:FindFirstChild("K.O") then
        return not player.Character.BodyEffects["K.O"].Value
    elseif player.Character and player.Character:FindFirstChild("Humanoid") then
        return player.Character.Humanoid.Health > 0
    end
    return false
end

-- Get closest player function
local function getClosestPlayer()
    local closestPlayer = nil
    local shortestDistance = math.huge

    for _, otherPlayer in pairs(Players:GetPlayers()) do
        if otherPlayer ~= Player and otherPlayer.Character then
            local part = otherPlayer.Character:FindFirstChild(Part)
            if part then
                local screenPosition, onScreen = Camera:WorldToScreenPoint(part.Position)
                local mousePosition = UserInputService:GetMouseLocation()
                local distance = (mousePosition - Vector2.new(screenPosition.X, screenPosition.Y)).Magnitude

                if distance < shortestDistance and distance < Circle.Radius and onScreen and alive(otherPlayer) then
                    shortestDistance = distance
                    closestPlayer = otherPlayer
                end
            end
        end
    end
    return closestPlayer
end

-- Notification function
local function sendNotification(title, text)
    pcall(function()
        game:GetService("StarterGui"):SetCore("SendNotification", {
            Title = title;
            Text = text;
            Duration = 5;
        })
    end)
end

-- Handle key press events
local function onKeyPress(input)
    local closestPlayer = getClosestPlayer()

    if input.KeyCode == Enum.KeyCode[KeybindAimbot] then
        if closestPlayer then
            if not Enabled then
                Enabled = true
                TargetPart = closestPlayer.Character:FindFirstChild(Part)
                Target = closestPlayer
                sendNotification("Aimbot", "Locked On To: " .. closestPlayer.Name)
                updateCamera() -- Update camera for smooth aim
            else
                Enabled = false
                TargetPart = nil
                Target = nil
                sendNotification("Aimbot", "Aimbot Disabled")
            end
        end
    elseif input.KeyCode == Enum.KeyCode[KeybindStrafe] then
        if StrafeEnabled and closestPlayer then
            sendNotification("Strafe", "Strafing On: " .. (Target and Target.Name or "Unknown"))
        end
    elseif input.KeyCode == Enum.KeyCode[KeybindResolver] then
        Resolver = not Resolver
        sendNotification("Resolver", Resolver and "Resolver Enabled" or "Resolver Disabled")
    elseif input.KeyCode == Enum.KeyCode[KeybindToggleMode] then
        UnderStrafeMode = not UnderStrafeMode
        sendNotification("Gun Mode", UnderStrafeMode and "Under Strafe" or "Normal Strafe")
    end
end

-- Update camera function
local function updateCamera()
    if AimbotEnabled and Enabled and TargetPart and alive(Target) then
        task.spawn(function()
            while Enabled and TargetPart and alive(Target) do
                local targetPosition = TargetPart.Position + (TargetPart.Velocity * MovementPrediction)
                local cameraPosition = Camera.CFrame.Position
                local directionToTarget = (targetPosition - cameraPosition).unit
                local lookAtPosition = targetPosition + directionToTarget * 10 

                Camera.CFrame = CFrame.new(cameraPosition, lookAtPosition)
                RunService.Heartbeat:Wait()
            end
        end)
    end
end

-- Update FOV Circle
local function updateCircle()
    Circle.Position = UserInputService:GetMouseLocation()
    Circle.Radius = FOV.Size
    Circle.Visible = FOV.Visible
end

-- Strafe functionality
local strafeActive = false
local strafeRotation = 0
local targetForStrafe = nil
local strafeSpeed = 10
local strafeHeight = 5
local strafeDistance = 5

UserInputService.InputBegan:Connect(function(input)
    if StrafeEnabled and input.KeyCode == Enum.KeyCode[KeybindStrafe] then
        strafeActive = not strafeActive
        targetForStrafe = getClosestPlayer()
    end
end)

-- Strafe movement logic
RunService.Heartbeat:Connect(function(delta)
    if strafeActive and StrafeEnabled and targetForStrafe and alive(targetForStrafe) then
        strafeRotation = strafeRotation + strafeSpeed * delta
        local strafeCFrame = UnderStrafeMode and 
            CFrame.Angles(0, 5 * math.pi * delta, 0) * CFrame.new(0, -strafeHeight, strafeDistance) or 
            CFrame.Angles(0, math.rad(strafeRotation), 0) * CFrame.new(0, strafeHeight, strafeDistance)
            
        Player.Character.HumanoidRootPart.CFrame = targetForStrafe.Character.HumanoidRootPart.CFrame * strafeCFrame

        if ViewAt then
            Camera.CameraSubject = targetForStrafe.Character.Humanoid
        else
            Camera.CameraSubject = Player.Character.Humanoid
        end
    elseif ViewAt then
        Camera.CameraSubject = Player.Character.Humanoid
    end
end)

-- Connections
UserInputService.InputBegan:Connect(onKeyPress)
RunService.Heartbeat:Connect(updateCircle)
RunService.Heartbeat:Connect(updateCamera)
end)

-- funtab 
local fucknigger = FunstuffPage.AddButton("FOV [PRO]", function()
    workspace.CurrentCamera.FieldOfView = 120
end)

local niggers = FunstuffPage.AddButton("Mask Names", function()
	for _, v in pairs(game:GetService("Players"):GetPlayers()) do
		if v and v.Character and v.Character:FindFirstChildOfClass("Humanoid") and v.Character:FindFirstChildOfClass("Humanoid").DisplayDistanceType ~= Enum.HumanoidDisplayDistanceType.Viewer then
			v.Character:FindFirstChildOfClass("Humanoid").DisplayDistanceType = Enum.HumanoidDisplayDistanceType.Viewer
		end
	end
end)

local niggers = FunstuffPage.AddButton("Anti-Fling", function()
-- // Constants \\ --
-- [ Services ] --
local Services = setmetatable({}, {__index = function(Self, Index)
local NewService = game.GetService(game, Index)
if NewService then
Self[Index] = NewService
end
return NewService
end})
 
-- [ LocalPlayer ] --
local LocalPlayer = Services.Players.LocalPlayer
 
-- // Functions \\ --
local function PlayerAdded(Player)
   local Detected = false
   local Character;
   local PrimaryPart;
 
   local function CharacterAdded(NewCharacter)
       Character = NewCharacter
       repeat
           wait()
           PrimaryPart = NewCharacter:FindFirstChild("HumanoidRootPart")
       until PrimaryPart
       Detected = false
   end
 
   CharacterAdded(Player.Character or Player.CharacterAdded:Wait())
   Player.CharacterAdded:Connect(CharacterAdded)
   Services.RunService.Heartbeat:Connect(function()
       if (Character and Character:IsDescendantOf(workspace)) and (PrimaryPart and PrimaryPart:IsDescendantOf(Character)) then
           if PrimaryPart.AssemblyAngularVelocity.Magnitude > 50 or PrimaryPart.AssemblyLinearVelocity.Magnitude > 100 then
               if Detected == false then
                   game.StarterGui:SetCore("ChatMakeSystemMessage", {
                       Text = "Fling Exploit Detected, Player: " .. tostring(Player);
                       Color = Color3.fromRGB(255, 255, 255);
                   })
               end
               Detected = true
               for i,v in ipairs(Character:GetDescendants()) do
                   if v:IsA("BasePart") then
                       v.CanCollide = false
                       v.AssemblyAngularVelocity = Vector3.new(0, 0, 0)
                       v.AssemblyLinearVelocity = Vector3.new(0, 0, 0)
                       v.CustomPhysicalProperties = PhysicalProperties.new(0, 0, 0)
                   end
               end
               PrimaryPart.CanCollide = false
               PrimaryPart.AssemblyAngularVelocity = Vector3.new(0, 0, 0)
               PrimaryPart.AssemblyLinearVelocity = Vector3.new(0, 0, 0)
               PrimaryPart.CustomPhysicalProperties = PhysicalProperties.new(0, 0, 0)
           end
       end
   end)
end
 
-- // Event Listeners \\ --
for i,v in ipairs(Services.Players:GetPlayers()) do
   if v ~= LocalPlayer then
       PlayerAdded(v)
   end
end
Services.Players.PlayerAdded:Connect(PlayerAdded)
 
local LastPosition = nil
Services.RunService.Heartbeat:Connect(function()
   pcall(function()
       local PrimaryPart = LocalPlayer.Character.PrimaryPart
       if PrimaryPart.AssemblyLinearVelocity.Magnitude > 250 or PrimaryPart.AssemblyAngularVelocity.Magnitude > 250 then
           PrimaryPart.AssemblyAngularVelocity = Vector3.new(0, 0, 0)
           PrimaryPart.AssemblyLinearVelocity = Vector3.new(0, 0, 0)
           PrimaryPart.CFrame = LastPosition
 
           game.StarterGui:SetCore("ChatMakeSystemMessage", {
               Text = "You Have Full Protect From Fling";
               Color = Color3.fromRGB(255, 255, 255);
           })
       elseif PrimaryPart.AssemblyLinearVelocity.Magnitude < 50 or PrimaryPart.AssemblyAngularVelocity.Magnitude > 50 then
           LastPosition = PrimaryPart.CFrame
       end
   end)
end)
end)
---------------
for i,v in pairs(game:GetService("Workspace").Ignored.Shop:GetChildren()) do
    local FirstButton = AutoBuyPage.AddButton(v.Name, function()
        local Pos = game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame
        local Teleport = v:FindFirstChild("Head")
        if Teleport then
            game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame = Teleport.CFrame
            local CD = v:FindFirstChild("ClickDetector")
            if CD then
				wait(1)
                fireclickdetector(CD)
                game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame = Pos
            end
        end
    end)
    end

        local EspButton = EspPage.AddButton("Chams Esp", function()
getgenv().esp = true
getgenv().Color = Color3.fromRGB(255, 255, 255)

		spawn(function()
		while wait() do
		    if not getgenv().esp then
		          for i,v in pairs(game.Players:GetChildren()) do
		              if v.Character and v.Character:FindFirstChild("Highlight") then
		                  local Highlight = v.Character:FindFirstChild("Highlight")
		                  Highlight.Enabled = false
    		      end
		      end 
		      else
		          for i,v in pairs(game.Players:GetChildren()) do
		             if getgenv().teamcheck == true then
		               if v.Character and v ~= game.Players.LocalPlayer and v.TeamColor ~= game.Players.LocalPlayer.TeamColor then
    		                 if v.Character:FindFirstChild("Highlight") then
    		                 local Highlight = v.Character:FindFirstChild("Highlight") 
    		                 Highlight.Enabled = true
    		                 Highlight.FillColor = getgenv().Color
    		                 Highlight.Adornee = v.Character
    		                 else
    		                 local Highlight = Instance.new("Highlight",v.Character)
    		                 Highlight.Enabled = true
    		                 Highlight.FillColor = getgenv().Color
    		                 Highlight.Adornee = v.Character
    		              end       
    		           end  
		                if v.TeamColor == game.Players.LocalPlayer.TeamColor then
    		              if v.Character and v.Character:FindFirstChild("Highlight") then
    		                  local Highlight = v.Character:FindFirstChild("Highlight")
        		              Highlight.Enabled = false
        		          end    
    		            end 
    		          else
    		              if v.Character and v ~= game.Players.LocalPlayer then
    		                 if v.Character:FindFirstChild("Highlight") then
    		                 local Highlight = v.Character:FindFirstChild("Highlight") 
    		                 Highlight.Enabled = true
    		                 Highlight.FillColor = getgenv().Color
    		                 Highlight.Adornee = v.Character
    		                 else
    		                 local Highlight = Instance.new("Highlight",v.Character)
    		                 Highlight.Enabled = true
    		                 Highlight.FillColor = getgenv().Color
    		                 Highlight.Adornee = v.Character
    		              end       
    		           end    
		            end       
		      end    
		    end  
		end    
		end)
        end)

---------------------------------------------------------------------------------------------------------------------------

    local EspButton = EspPage.AddButton("Sky Box", function()
    local galaxySkybox = {
    SkyboxBk = "rbxassetid://155657655",
    SkyboxDn = "rbxassetid://155674246",
    SkyboxFt = "rbxassetid://155657609",
    SkyboxLf = "rbxassetid://155657671",
    SkyboxRt = "rbxassetid://155657619",
    SkyboxUp = "rbxassetid://155674931"
}

local function disableExistingSkybox()
    local sky = game.Lighting:FindFirstChildOfClass("Sky")
    if sky then
        sky:Destroy()
    end
end

local function setGalaxySkybox()
    local sky = Instance.new("Sky")
    sky.Name = "GalaxySkybox"
    sky.Parent = game.Lighting
    
    sky.SkyboxBk = galaxySkybox.SkyboxBk
    sky.SkyboxDn = galaxySkybox.SkyboxDn
    sky.SkyboxFt = galaxySkybox.SkyboxFt
    sky.SkyboxLf = galaxySkybox.SkyboxLf
    sky.SkyboxRt = galaxySkybox.SkyboxRt
    sky.SkyboxUp = galaxySkybox.SkyboxUp
end

local function monitorSkybox()
    while true do
        disableExistingSkybox()
        setGalaxySkybox()
        wait(1) 
    end
end

setGalaxySkybox()
monitorSkybox()
end)

    local EspButton = EspPage.AddButton("Name Esp", function()
local esp_settings = { ---- table for esp settings 
    textsize = 20,
    colour = 255,255,255
}
 
local gui = Instance.new("BillboardGui")
local esp = Instance.new("TextLabel",gui) ---- new instances to make the billboard gui and the textlabel
 
 
 
gui.Name = "Cracked esp"; ---- properties of the esp
gui.ResetOnSpawn = false
gui.AlwaysOnTop = true;
gui.LightInfluence = 0;
gui.Size = UDim2.new(2, 0, 2, 0);
esp.BackgroundColor3 = Color3.fromRGB(255,255,255);
esp.Text = ""
esp.Size = UDim2.new(0.0001, 0.00001, 0.0001, 0.00001);
esp.BorderSizePixel = 4;
esp.BorderColor3 = Color3.new(esp_settings.colour)
esp.BorderSizePixel = 0
esp.Font = "GothamSemibold"
esp.TextSize = esp_settings.textsize
esp.TextColor3 = Color3.fromRGB(esp_settings.colour) -- text colour
 
game:GetService("RunService").RenderStepped:Connect(function() ---- loops faster than a while loop :)
    for i,v in pairs (game:GetService("Players"):GetPlayers()) do
        if v ~= game:GetService("Players").LocalPlayer and v.Character.Head:FindFirstChild("Cracked esp")==nil  then -- craeting checks for team check, local player etc
            esp.Text = "{"..v.Name.."}"
            gui:Clone().Parent = v.Character.Head
    end
end
end)
end)

   local EspButton = EspPage.AddButton("Bullet Tracer", function()
   BulletTracers = true
bullet_tracer_color = Color3.fromRGB(255, 0, 0)
---------------------------------------------------------------------------------------------------------------------------

function GetGun()
	if game.Players.LocalPlayer.Character then
		for i, v in pairs(game.Players.LocalPlayer.Character:GetChildren()) do
			if v:FindFirstChild 'Ammo' then
				return v
			end
		end
	end
	return nil
end

local Services = {
	Players = game:GetService("Players"),
	UserInputService = game:GetService("UserInputService"),
	RunService = game:GetService("RunService"),
}

local Local = {
	Player = Services.Players.LocalPlayer,
	Mouse = Services.Players.LocalPlayer:GetMouse(),
}
local Other = {
	Camera = workspace.CurrentCamera,
	BeamPart = Instance.new("Part", workspace)
}

Other.BeamPart.Name = "BeamPart"
Other.BeamPart.Transparency = 1
local Settings = {
	StartColor = bullet_tracer_color,
	EndColor = bullet_tracer_color,
	StartWidth = 3,
	EndWidth = 3,
	ShowImpactPoint = false,
	ImpactTransparency = 0.5,
	ImpactColor = Color3.new(1, 1, 1),
	Time = 1,
}
game:GetService "RunService".Heartbeat:Connect(function()
	if game:GetService("Workspace").Ignored:FindFirstChild 'BULLET_RAYS' and BulletTracers then
		game:GetService("Workspace").Ignored.BULLET_RAYS:Destroy()
	end
end)
local funcs = {}
Local.Mouse.TargetFilter = Other.BeamPart
function funcs:Beam(v1, v2)
	v2 = Vector3.new(v2.X - 0.1, v2.Y + 0.2, v2.Z)
	local colorSequence = ColorSequence.new({
		ColorSequenceKeypoint.new(0, bullet_tracer_color),
		ColorSequenceKeypoint.new(1, bullet_tracer_color),
	})
	local Part = Instance.new("Part", Other.BeamPart)
	Part.Size = Vector3.new(0, 0, 0)
	Part.Massless = true
	Part.Transparency = 1
	Part.CanCollide = false
	Part.Position = v1
	Part.Anchored = true
	local Attachment = Instance.new("Attachment", Part)
	local Part2 = Instance.new("Part", Other.BeamPart)
	Part2.Size = Vector3.new(0, 0, 0)
	Part2.Transparency = 0
	Part2.CanCollide = false
	Part2.Position = v2
	Part2.Anchored = true
	Part2.Material = Enum.Material.ForceField
	Part2.Color = Settings.ImpactColor
	Part2.Massless = true
	local Attachment2 = Instance.new("Attachment", Part2)
	local Beam = Instance.new("Beam", Part)
	Beam.FaceCamera = true
	Beam.Color = colorSequence
	Beam.Attachment0 = Attachment
	Beam.Attachment1 = Attachment2
	Beam.LightEmission = 6
	Beam.LightInfluence = 1
	Beam.Width0 = Settings.StartWidth
	Beam.Width1 = Settings.EndWidth
	Beam.Texture = "http://www.roblox.com/asset/?id=9150663556"
	Beam.TextureSpeed = 2
	Beam.TextureLength = 1
	delay(Settings.Time, function()
		Part:Destroy()
		Part2:Destroy()
	end)
end

spawn(function()
	while task.wait(0.5) do
		gun = GetGun()
		if gun then
			LastAmmo = gun.Ammo.Value
			gun.Ammo:GetPropertyChangedSignal("Value"):Connect(function()
				if BulletTracers and gun.Ammo.Value < LastAmmo then
					LastAmmo = gun.Ammo.Value
					funcs:Beam(gun.Handle.Position, Local.Mouse.hit.p)
				end
			end)
		end
	end
end)
end)

    local EspButton = EspPage.AddButton("Esp Box", function()
    local Player = game:GetService("Players").LocalPlayer
local Camera = game:GetService("Workspace").CurrentCamera
local Mouse = Player:GetMouse()

local function DrawESP(plr)
    local Box = Drawing.new("Quad")
    Box.Visible = false
    Box.PointA = Vector2.new(0, 0)
    Box.PointB = Vector2.new(0, 0)
    Box.PointC = Vector2.new(0, 0)
    Box.PointD = Vector2.new(0, 0)
    Box.Color = Color3.fromRGB(255, 255, 255)
    Box.Thickness = 1
    Box.Transparency = 1

    local function Update()
        local c
        c = game:GetService("RunService").RenderStepped:Connect(function()
            if plr.Character ~= nil and plr.Character:FindFirstChildOfClass("Humanoid") ~= nil and plr.Character.PrimaryPart ~= nil and plr.Character:FindFirstChildOfClass("Humanoid").Health > 0 then
                local pos, vis = Camera:WorldToViewportPoint(plr.Character.PrimaryPart.Position)
                if vis then 
                    local points = {}
                    local c = 0
                    for _,v in pairs(plr.Character:GetChildren()) do
                        if v:IsA("BasePart") then
                            c = c + 1
                            local p, vis = Camera:WorldToViewportPoint(v.Position)
                            if v == plr.Character.PrimaryPart then
                                p, vis = Camera:WorldToViewportPoint((v.CFrame * CFrame.new(0, 0, -v.Size.Z)).p)
                            elseif v.Name == "Head" then
                                p, vis = Camera:WorldToViewportPoint((v.CFrame * CFrame.new(0, v.Size.Y/2, v.Size.Z/1.25)).p)
                            elseif string.match(v.Name, "Left") then
                                p, vis = Camera:WorldToViewportPoint((v.CFrame * CFrame.new(-v.Size.X/2, 0, 0)).p)
                            elseif string.match(v.Name, "Right") then
                                p, vis = Camera:WorldToViewportPoint((v.CFrame * CFrame.new(v.Size.X/2, 0, 0)).p)
                            end
                            points[c] = {p, vis}
                        end
                    end

                    local TopY = math.huge
                    local DownY = -math.huge
                    local LeftX = math.huge
                    local RightX = -math.huge

                    local Left
                    local Right
                    local Top
                    local Bottom

                    local closest = nil
                    for _,v in pairs(points) do
                        if v[2] == true then
                            local p = v[1]
                            if p.Y < TopY then
                                Top = p
                                TopY = p.Y
                            end
                            if p.Y > DownY then
                                Bottom = p
                                DownY = p.Y
                            end
                            if p.X > RightX then
                                Right = p
                                RightX = p.X
                            end
                            if p.X < LeftX then
                                Left = p
                                LeftX = p.X
                            end
                        end
                    end

                    if Left ~= nil and Right ~= nil and Top ~= nil and Bottom ~= nil then
                        Box.PointA = Vector2.new(Right.X, Top.Y)
                        Box.PointB = Vector2.new(Left.X, Top.Y)
                        Box.PointC = Vector2.new(Left.X, Bottom.Y)
                        Box.PointD = Vector2.new(Right.X, Bottom.Y)

                        Box.Visible = true
                    else 
                        Box.Visible = false
                    end
                else 
                    Box.Visible = false
                end
            else
                Box.Visible = false
                if game.Players:FindFirstChild(plr.Name) == nil then
                    c:Disconnect()
                end
            end
        end)
    end
    coroutine.wrap(Update)()
end

for _,v in pairs(game:GetService("Players"):GetChildren()) do
    if v.Name ~= Player.Name then 
        DrawESP(v)
    end
end

game:GetService("Players").PlayerAdded:Connect(function(v)
    DrawESP(v)
end)
end)

local AntilockButton = AntilockPage.AddButton("Custom Anti lock", function()
getgenv().Anti = true 
getgenv().X = 1000 
getgenv().Y = 991
getgenv().Z = 100

game:GetService("RunService").heartbeat:Connect(function()
    if getgenv().Anti == true then 
    local vel = game.Players.LocalPlayer.Character.HumanoidRootPart.Velocity
    game.Players.LocalPlayer.Character.HumanoidRootPart.Velocity = Vector3.new(getgenv().X,getgenv().Y,getgenv().Z) 
    game:GetService("RunService").RenderStepped:Wait()
    game.Players.LocalPlayer.Character.HumanoidRootPart.Velocity = vel
    end 
end)
end)

local AntilockButton = AntilockPage.AddButton("Multiplier (Z)", function()
local ToggleKey = "z" -- Change that to whatever keybind: "t"
local Power = 50
    
local Desync = true
local RunS = game:GetService("RunService")
local LocalPlayer = game:GetService("Players").LocalPlayer
local Mouse = LocalPlayer:GetMouse()

local Marker = Instance.new("Part", game.Workspace)
Marker.Anchored = true
Marker.CanCollide = true
Marker.Transparency = 1

local Billb = Instance.new("BillboardGui", Marker)
Billb.Name = "PP"
Billb.Adornee = Marker
Billb.Size = UDim2.new(0.60, 0,  0.60, 0)
Billb.AlwaysOnTop = true

local Frame = Instance.new("Frame", Billb)
Frame.Size = UDim2.new(1, 0, 1, 0)
Frame.Transparency = 0
Frame.BackgroundTransparency = 0
Frame.BackgroundColor3 = Color3.fromRGB(255, 115, 174)

local Corner = Instance.new("UICorner", Frame)
Corner.CornerRadius = UDim.new(0, 0)
Corner.CornerRadius = UDim.new(1, 1)

Mouse.KeyDown:Connect(function(Key)
    if Key == string.lower(ToggleKey) then
        Desync = not Desync
        game.StarterGui:SetCore("SendNotification", {
            Title = "Enable";
            Text = "Velocity Multi = ".. tostring(Desync),
            Duration = 1
        })
    end
end)

local DesyncTypes = {}
RunS.Heartbeat:Connect(function()
    if Desync == true and LocalPlayer.Character and LocalPlayer.Character:FindFirstChild("HumanoidRootPart") then
        DesyncTypes[1] = LocalPlayer.Character.HumanoidRootPart.CFrame
        DesyncTypes[2] = LocalPlayer.Character.HumanoidRootPart.AssemblyLinearVelocity

        LocalPlayer.Character.HumanoidRootPart.AssemblyLinearVelocity = LocalPlayer.Character.HumanoidRootPart.AssemblyLinearVelocity * Power
        
        Marker.CFrame = (LocalPlayer.Character.HumanoidRootPart.CFrame + LocalPlayer.Character.HumanoidRootPart.AssemblyLinearVelocity * 3)
        
        RunS.RenderStepped:Wait()

        LocalPlayer.Character.HumanoidRootPart.CFrame = DesyncTypes[1]
        LocalPlayer.Character.HumanoidRootPart.AssemblyLinearVelocity = DesyncTypes[2]
    else
        Marker.CFrame = CFrame.new(10000,10000,10000)
    end
end)

local Hook = nil
Hook = hookmetamethod(game, "__index", newcclosure(function(self, key)
    if Desync == true then
        if not checkcaller() then
            if key == "CFrame" and Desync == true and LocalPlayer.Character and LocalPlayer.Character:FindFirstChild("HumanoidRootPart") and LocalPlayer.Character:FindFirstChild("Humanoid") and LocalPlayer.Character:FindFirstChild("Humanoid").Health > 0 then
                if self == LocalPlayer.Character.HumanoidRootPart then
                    return DesyncTypes[1] or CFrame.new()
                elseif self == LocalPlayer.Character.Head then
                    return DesyncTypes[1] and DesyncTypes[1] + Vector3.new(0, LocalPlayer.Character.HumanoidRootPart.Size / 3 + 1, 0) or CFrame.new()
                end
            end
        end
    end
    return Hook(self, key)
end))
end)


local AntilockButton = AntilockPage.AddButton("Desync", function()
-- Highest Roblox velocity is 128^2 or 16384
local velMax = (128 ^ 2)

-- Time to release and choke the replication packets
local timeRelease, timeChoke = 0.015, 0.105

-- Function aliases
local Property, Wait = sethiddenproperty, wait
local Radian, Random, Ceil = math.rad, math.random, math.ceil
local Angle = CFrame.Angles
local Vector = Vector3.new
local Service = game.GetService

-- Services
local Run = Service(game, 'RunService')
local statPing = Service(game, 'Stats').PerformanceStats.Ping
local Root = Service(game, 'Players').LocalPlayer.Character.HumanoidRootPart

-- Connections
local runRen, runBeat = Run.RenderStepped, Run.Heartbeat
local runRenWait, runRenCon = runRen.Wait, runRen.Connect
local runBeatCon = runBeat.Connect

-- Ping function
local Ping = statPing.GetValue

-- Client replication choking/sleeping
local function Sleep()
    return Property(Root, 'NetworkIsSleeping', true)
end

-- Initialization
local function Init()
    local rootVel = Root.Velocity
    local rootAng = Random(-180, 180)
    local rootOffset do
        local X = Random(-velMax, velMax)
        local Y = Random(0, velMax)
        local Z = Random(-velMax, velMax)
        rootOffset = Vector(X, -Y, Z)
    end

    Root.CFrame *= Angle(0, Radian(rootAng), 0)
    Root.Velocity = rootOffset

    -- Miscellaneous velocities (rotational, linear, angular)
    runRenWait(runRen) -- Quickly synchronize velocity before rendering (smoother?)
    Root.CFrame *= Angle(0, Radian(-rootAng), 0)
    Root.Velocity = rootVel
end

-- Keep initialization running in the background
runBeatCon(runBeat, Init)

-- Main loop
while Wait(timeRelease) do
    -- Stable replication packets
    local chokeClient, chokeServer = runBeatCon(runBeat, Sleep), runRenCon(runRen, Sleep)

    -- Ping-based choke time (replace with timeChoke for constant time)
    Wait(Ceil(Ping(statPing)) / 1000)

    -- Disconnect choke connections
    chokeClient:Disconnect()
    chokeServer:Disconnect()
end
end)

local AntilockButton = AntilockPage.AddButton("Destroy Cheaters (X)", function()
local FINOBE_KEYBIND = "X"

getgenv().Finobe1 = true 
local NewCFrame = CFrame.new
local LocalFinobe = game.Players.LocalPlayer
local InputService = game:GetService("UserInputService")
local Runfinobe = game:GetService("RunService")

local Finobe2; 
Runfinobe.heartbeat:Connect(function()
    if LocalFinobe.Character then 
        local FinobeChar = LocalFinobe.Character.HumanoidRootPart
        local Offset = FinobeChar.CFrame * NewCFrame(9e9, 0/0, math.huge)
        
        if getgenv().Finobe1 then 
            Finobe2 = FinobeChar.CFrame
            FinobeChar.CFrame = Offset
            Runfinobe.RenderStepped:Wait()
            FinobeChar.CFrame = Finobe2
        end 
    end 
end)

InputService.InputBegan:Connect(function(sigma)
    if sigma.KeyCode == Enum.KeyCode[FINOBE_KEYBIND] then 
        getgenv().Finobe1 = not getgenv().Finobe1
        
        if not Finobe1 then 
            LocalFinobe.Character.HumanoidRootPart.CFrame = Finobe2
            -- 
            game:GetService("StarterGui"):SetCore("SendNotification",{
                Title = "Destroy Exploiters";
                Text = "Disabled";
            })
        else 
            Finobe2 = nil 
            -- 
            game:GetService("StarterGui"):SetCore("SendNotification",{
                Title = "Destroy Exploiters";
                Text = "Enabled";
            })
        end 
    end 
end)    

local finobeHookSigmaChatWtfCreateRemindedMeAboutThisShittyAssExploitBtw_MiseryOwnerIsACuck
finobeHookSigmaChatWtfCreateRemindedMeAboutThisShittyAssExploitBtw_MiseryOwnerIsACuck = hookmetamethod(game, "__index", newcclosure(function(self, key)
    if not checkcaller() then
        if key == "CFrame" and getgenv().Finobe1 and LocalFinobe.Character and LocalFinobe.Character:FindFirstChild("HumanoidRootPart") and LocalFinobe.Character:FindFirstChild("Humanoid") and LocalFinobe.Character:FindFirstChild("Humanoid").Health > 0 then
            if self == LocalFinobe.Character.HumanoidRootPart and Finobe2 ~= nil then
                return Finobe2
            end
        end
    end
    -- 
    return finobeHookSigmaChatWtfCreateRemindedMeAboutThisShittyAssExploitBtw_MiseryOwnerIsACuck(self, key)
end))
end)

local AntilockButton = AntilockPage.AddButton("Random Teleport (Z)", function()
local RunService = game:GetService("RunService")

getgenv().refusal = {
    Key = Enum.KeyCode.Z,
    Enabled = false,
}

game:GetService("UserInputService").InputBegan:Connect(function(input)
    if input.KeyCode == getgenv().refusal.Key then
        getgenv().refusal.Enabled = not getgenv().refusal.Enabled

        if getgenv().refusal.Enabled then
            game.StarterGui:SetCore("SendNotification", {
                Title = "Random Teleport Enable",
                Text = "On",
                Duration = 1,
            })
        else
            game.StarterGui:SetCore("SendNotification", {
                Title = "Random Teleport Disable",
                Text = "Off",
                Duration = 1,
            })
        end
    end
end)

RunService.Heartbeat:Connect(function()
    if getgenv().refusal.Enabled then
        local v = game.Players.LocalPlayer.Character.HumanoidRootPart.Velocity
        game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame = game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame * CFrame.Angles(0, math.rad(0.01), 0)

        local randomVelocity = Vector3.new(
            math.random(-10000, 10000),
            math.random(-10000, 10000),
            math.random(-10000, 10000)
        )

        game.Players.LocalPlayer.Character.HumanoidRootPart.Velocity = randomVelocity
        RunService.RenderStepped:Wait()
        game.Players.LocalPlayer.Character.HumanoidRootPart.Velocity = v
    end
end)
end)

local AntilockButton = AntilockPage.AddButton("Sky Anti lock", function()
getgenv().Sky = true 
game:GetService("RunService").heartbeat:Connect(function()
    if getgenv().Sky == true then 
    local vel = game.Players.LocalPlayer.Character.HumanoidRootPart.Velocity
    game.Players.LocalPlayer.Character.HumanoidRootPart.Velocity = Vector3.new(0,250,0) 
    game:GetService("RunService").RenderStepped:Wait()
    game.Players.LocalPlayer.Character.HumanoidRootPart.Velocity = vel
    end 
end)
end)

local CreditsButton = CreditsPage.AddButton("Saad - Owner", function()
    setclipboard("Saad") 
end)

            assert(getrawmetatable)
            grm = getrawmetatable(game)
            setreadonly(grm, false)
            old = grm.__namecall
            grm.__namecall = newcclosure(function(self, ...)
                local args = {...}
                if tostring(args[1]) == "TeleportDetect" then
                    return
                elseif tostring(args[1]) == "CHECKER_1" then
                    return
                elseif tostring(args[1]) == "CHECKER" then
                    return
                elseif tostring(args[1]) == "GUI_CHECK" then
                    return
                elseif tostring(args[1]) == "OneMoreTime" then
                    return
                elseif tostring(args[1]) == "checkingSPEED" then
                    return
                elseif tostring(args[1]) == "BANREMOTE" then
                    return
                elseif tostring(args[1]) == "PERMAIDBAN" then
                    return
                elseif tostring(args[1]) == "KICKREMOTE" then
                    return
                elseif tostring(args[1]) == "BR_KICKPC" then
                    return
                elseif tostring(args[1]) == "BR_KICKMOBILE" then
                    return
                end
                return old(self, ...)
            end)
